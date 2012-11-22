package com.cs410.getfit.client.view;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.events.MapEventType;
import com.google.gwt.maps.client.events.MapHandlerRegistration;
import com.google.gwt.maps.client.events.click.ClickMapEvent;
import com.google.gwt.maps.client.events.click.ClickMapHandler;
import com.google.gwt.maps.client.events.resize.ResizeMapEvent;
import com.google.gwt.maps.client.events.resize.ResizeMapHandler;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.services.Geocoder;
import com.google.gwt.maps.client.services.GeocoderRequest;
import com.google.gwt.maps.client.services.GeocoderRequestHandler;
import com.google.gwt.maps.client.services.GeocoderResult;
import com.google.gwt.maps.client.services.GeocoderStatus;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;




/**
 * @author syred
 * PositionCallback
 * Handles the position received from the user
 */
public class PositionCallback implements Callback<Object, Object>{
	
	private MapWidget mapWidget;
	private HorizontalPanel gMapsPanel;
	private Hidden locationBox;
	
	/**
	 * Constructor for PositionCallback
	 * @param mapWidget
	 * @param gMapsPanel
	 * @param locationBox
	 */
	public PositionCallback(MapWidget mapWidget, HorizontalPanel gMapsPanel, Hidden locationBox) {
		this.mapWidget = mapWidget;
		gMapsPanel.clear();
		this.gMapsPanel = gMapsPanel;
		this.locationBox = locationBox;
	}
	
	/* 
	 * Get location callback on failure method
	 * @see com.google.gwt.core.client.Callback#onFailure(java.lang.Object)
	 */
	@Override
	public void onFailure(Object positionErr) {
		PositionError error = (PositionError) positionErr;
		System.out.println("NOOOOOOOOOOO");
		System.out.println(error.getMessage());
		setUpMap(null);
	}

	/* 
	 * Get location callback on success method
	 * @param result: The location of the user
	 * @see com.google.gwt.core.client.Callback#onSuccess(java.lang.Object)
	 */
	@Override
	public void onSuccess(Object result) {
		Position pos = (Position) result;
		Coordinates coor = pos.getCoordinates();
		setUpMap(coor);
	}
	
	private void setUpMap(Coordinates coor) {
		LatLng userPoint;
		// Regular expresion for latitude and longitude as stored in DATABASE
		RegExp regexp = RegExp.compile("\\((\\-?\\d+(\\.\\d+)?),\\s*(\\-?\\d+(\\.\\d+)?)\\)");
		MatchResult match = regexp.exec(locationBox.getValue());
		if (locationBox.getValue() != null && locationBox.getValue() != "" &&  match.getGroupCount() == 5) {
			userPoint = LatLng.newInstance(Double.parseDouble(match.getGroup(1)), Double.parseDouble(match.getGroup(3))); 
		} else if (coor != null){
			// Set the user location
			userPoint =  LatLng.newInstance(coor.getLatitude(), coor.getLongitude());
			locationBox.setValue(userPoint.getToString());
		} else {
			// No user location and no location sent from database
			// Set it to center of BC
			userPoint =  LatLng.newInstance(56.0475,-126.613037);
			locationBox.setValue(userPoint.getToString());
		}
		
		// Marker overlay to show user's position on map
		final Marker userPosMarker = Marker.newInstance(null);
		userPosMarker.setPosition(userPoint);
				
		final InfoWindow infoWindow = InfoWindow.newInstance(null);
		
		
		final MapOptions options = MapOptions.newInstance();
	    // Zoom level. Required
	    options.setZoom(10);
	    // Open a map centered. Required
	    options.setCenter(userPoint);
	    // Map type. Required.
	    options.setMapTypeId(MapTypeId.ROADMAP);
	    // Enable maps drag feature. Disabled by default.
	    options.setDraggable(true);
	    // Enable and add default navigation control. Disabled by default.
	    options.setPanControl(true);
	    options.setScaleControl(true);
	    options.setZoomControl(true);
	    // Enable and add map type control. Disabled by default.
	    options.setMapTypeControl(true);
	    mapWidget = new MapWidget(options);
	    mapWidget.setSize("500px", "500px");
	    
	    // Add click handler to let the user change the marker's position
	    mapWidget.addClickHandler(new ClickMapHandler() {
	    	public void onEvent(ClickMapEvent event) {
	    		LatLng point = event.getMouseEvent().getLatLng();
	    		userPosMarker.setPosition(point);
	    		locationBox.setValue(point.getToString());
	    		setHumanReadableLocation(userPosMarker.getPosition(), infoWindow, mapWidget);
			}
	    });
	    
	    
	    // Set the marker to the map
	    userPosMarker.setMap(mapWidget);
	    setHumanReadableLocation(userPosMarker.getPosition(), infoWindow, mapWidget);
	    gMapsPanel.add(mapWidget);
	    
	    // Set the resizeHandler to have a correct parsed map
	    mapWidget.addResizeHandler(new ResizeMapHandler() {
	        @Override
	        public void onEvent(ResizeMapEvent event) {
	            GWT.log("Map has been resized!");
	        }
	    });
	    MapHandlerRegistration.trigger(mapWidget, MapEventType.RESIZE);
	}
	
	public MapWidget getMapWidget() {
		return this.mapWidget;
	}
	
	/**
	 * Uses a LatLng of a point to get the human readable location from the geocoder API. And sets it on a infowindow
	 * @param locationPoint
	 * @param infoWindow
	 * @param mapWidget
	 */
	public void setHumanReadableLocation(final LatLng locationPoint, final InfoWindow infoWindow, final MapWidget mapWidget) {
		Geocoder geocoder = Geocoder.newInstance();
		GeocoderRequest request = GeocoderRequest.newInstance();
		request.setLocation(locationPoint);
		geocoder.geocode(request, new GeocoderRequestHandler() {
			@Override
			public void onCallback(JsArray<GeocoderResult> results,
					GeocoderStatus status) {
				// Get the full location
				String formattedAddress = results.get(0).getFormatted_Address();
				String cityLocation = (formattedAddress != null && formattedAddress != "") ? formattedAddress : "N/A";
				// Set the full location to the infoWindow
				infoWindow.setContent(cityLocation);
				infoWindow.setPosition(locationPoint);
				infoWindow.open(mapWidget);
			}
		});
	}
}

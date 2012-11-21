package com.cs410.getfit.client.view;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.InfoWindowOptions;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.events.click.ClickMapEvent;
import com.google.gwt.maps.client.events.click.ClickMapHandler;
import com.google.gwt.maps.client.events.resize.ResizeMapEvent;
import com.google.gwt.maps.client.events.resize.ResizeMapHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.services.Geocoder;
import com.google.gwt.maps.client.services.GeocoderRequest;
import com.google.gwt.maps.client.services.GeocoderRequestHandler;
import com.google.gwt.maps.client.services.GeocoderResult;
import com.google.gwt.maps.client.services.GeocoderStatus;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;




public class PositionCallback implements Callback<Object, Object>{
	
	private MapWidget mapWidget;
	private HorizontalPanel gMapsPanel;
	private Hidden locationBox;
	
	public PositionCallback(MapWidget mapWidget, HorizontalPanel gMapsPanel, Hidden locationBox) {
		this.mapWidget = mapWidget;
		gMapsPanel.clear();
		this.gMapsPanel = gMapsPanel;
		this.locationBox = locationBox;
	}
	
	@Override
	public void onFailure(Object positionErr) {
		PositionError error = (PositionError) positionErr;
		System.out.println(error.getMessage());
		locationBox.setValue("");
		mapWidget = new MapWidget(null);
		mapWidget.setSize("500px", "500px");
		final Marker userPosMarker = Marker.newInstance(null);
		
		// Add click handler to let the user change the marker's position
		mapWidget.addClickHandler(new ClickMapHandler() {
			public void onEvent(ClickMapEvent event) {
				LatLng point = event.getMouseEvent().getLatLng();
				userPosMarker.setPosition(point);
				locationBox.setValue(point.getToString());
				// Get distance between two points
			// LatLng toPoint = LatLng.newInstance(25.792194, -108.996220);
			// System.out.println("Distance: " + SphericalUtils.computeDistanceBetween(point, toPoint) / 1000 + " km");
			}
		});
		gMapsPanel.add(mapWidget);
	}

	@Override
	public void onSuccess(Object result) {
		Position pos = (Position) result;
		Coordinates coor = pos.getCoordinates();
		LatLng userPoint =  LatLng.newInstance(coor.getLatitude(), coor.getLongitude());
		// Marker overlay to show user's position on map
		final Marker userPosMarker = Marker.newInstance(null);
		userPosMarker.setPosition(userPoint);
		locationBox.setValue(userPoint.getToString());
		userPosMarker.setTitle("Your Location");
		
		final InfoWindow infoWindow = InfoWindow.newInstance(null);
		
		
		final MapOptions options = MapOptions.newInstance();
	    // Zoom level. Required
	    options.setZoom(10);
	    // Open a map centered on Cawker City, KS USA. Required
	    options.setCenter(LatLng.newInstance(coor.getLatitude(), coor.getLongitude()));
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
	}
	
	public MapWidget getMapWidget() {
		return this.mapWidget;
	}
	
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
				infoWindow.setContent(cityLocation);
				infoWindow.setPosition(locationPoint);
				infoWindow.open(mapWidget);
			}
		});
	}
}

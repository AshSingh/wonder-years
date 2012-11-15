package com.cs410.getfit.client.presenter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cs410.getfit.client.event.GoToErrorEvent;
import com.cs410.getfit.client.json.ChallengesJsonFormatter;
import com.cs410.getfit.client.json.HTTPRequestBuilder;
import com.cs410.getfit.shared.OutgoingChallengeJsonModel;
import com.google.gwt.core.client.Callback;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.maps.client.events.MapHandlerRegistration;
import com.google.gwt.maps.client.events.click.ClickMapEvent;
import com.google.gwt.maps.client.events.click.ClickMapHandler;
import com.google.gwt.maps.client.geometrylib.SphericalUtils;



public class PositionSortCallback implements Callback<Object, Object>{
	
	private HandlerManager eventBus;
	private ChallengesPresenter presenter;

	public PositionSortCallback(HandlerManager eventBus, ChallengesPresenter presenter) {	
		this.eventBus = eventBus;
		this.presenter = presenter;
	}
	
	@Override
	public void onFailure(Object positionErr) {
		PositionError error = (PositionError) positionErr;
		System.out.println(error.getMessage());
	}

	@Override
	public void onSuccess(Object result) {
		Position pos = (Position) result;
		Coordinates coor = pos.getCoordinates();
		final LatLng userPoint =  LatLng.newInstance(coor.getLatitude(), coor.getLongitude());
		// Marker overlay to show user's position on map
		
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest("/challenges"); 
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						List<OutgoingChallengeJsonModel> models = ChallengesJsonFormatter.parseChallengeJsonInfo(response.getText());
						if (models.size() > 0) {
							for (OutgoingChallengeJsonModel model : models) {
								String modelLocation = model.getInfo().getLocation();
								// Regular expresion for latitude and longitude as stored in DATABASE
								RegExp regexp = RegExp.compile("\\((\\-?\\d+(\\.\\d+)?),\\s*(\\-?\\d+(\\.\\d+)?)\\)");
								MatchResult match = regexp.exec(modelLocation);
								LatLng challengePoint = LatLng.newInstance(Double.parseDouble(match.getGroup(1)), Double.parseDouble(match.getGroup(3)));
								// Calculate Distances
								double distance = SphericalUtils.computeDistanceBetween(userPoint, challengePoint); 
								model.setDistance(distance);
							}
						}
						Collections.sort(models, COMPARATOR);
						if (models.size() > 0) {
							for (OutgoingChallengeJsonModel model : models) {
								presenter.receiveSortedModels(model);
							}
						}
					}
					else {
						eventBus.fireEvent(new GoToErrorEvent(response.getStatusCode()));
					}
				}
				@Override
				public void onError(Request request, Throwable exception) {
					eventBus.fireEvent(new GoToErrorEvent());
				}
			});
		} catch (RequestException e) {
			eventBus.fireEvent(new GoToErrorEvent());
		}
		
	}
	
	private static Comparator<OutgoingChallengeJsonModel> COMPARATOR = new Comparator<OutgoingChallengeJsonModel>()
    {
	// This is where the sorting happens.
        public int compare(OutgoingChallengeJsonModel o1, OutgoingChallengeJsonModel o2)
        {
            return (int)(o1.getDistance() - o2.getDistance());
        }
    };
	
}

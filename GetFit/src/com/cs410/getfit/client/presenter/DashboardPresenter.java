package com.cs410.getfit.client.presenter;

import com.cs410.getfit.client.event.GoToCreateChallengeEvent;
import com.cs410.getfit.client.event.GoToErrorEvent;
import com.cs410.getfit.client.json.HTTPRequestBuilder;
import com.cs410.getfit.client.view.DashboardView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.HasWidgets;

public class DashboardPresenter implements Presenter, DashboardView.Presenter{

	private final HandlerManager eventBus;
	private final DashboardView view;
	
	public DashboardPresenter(HandlerManager eventBus, DashboardView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
	    container.add(view.asWidget());
	    populateView();
	}
	
	private void populateView(){
		// set title as current user's name
		// TODO: missing server side implementation of GET on users/id
		//setName();
		// TODO: missing server side implementation of GET on challenges?user=id
		//displayChallenges();
	}
	
	private void setName() {
		// GET request on user guid
		long userGuid = Long.parseLong(Cookies.getCookie("guid"));
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest("users/" + userGuid); 
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						// TODO: parse json response to a user object
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
	
	// display a list of challenges that user is participating in
	private void displayChallenges(){
		long userGuid = Long.parseLong(Cookies.getCookie("guid"));
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest("challenges/?user=" + userGuid); 
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						// TODO: parse response of challenges user is a part of
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
		
	public void onNewChallengeButtonClicked(){
		eventBus.fireEvent(new GoToCreateChallengeEvent());
	}
}

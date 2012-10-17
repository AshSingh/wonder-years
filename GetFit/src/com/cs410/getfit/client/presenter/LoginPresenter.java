package com.cs410.getfit.client.presenter;

import com.cs410.getfit.client.LoginView;
import com.cs410.getfit.client.event.GoToRegisterEvent;
import com.cs410.getfit.client.event.LoginEvent;
import com.cs410.getfit.client.event.RegisterEvent;
import com.cs410.getfit.client.JsonData;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class LoginPresenter implements Presenter, LoginView.Presenter{

	private final HandlerManager eventBus;
	private final LoginView view;
	
	public LoginPresenter(HandlerManager eventBus, LoginView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
	    container.add(view.asWidget());
	}
	
	@Override
	public void onLoginButtonClicked() {
		JsonData loginData = new JsonData();
		int httpResponse = loginData.loginRequest(view.getUsername(), view.getPassword());
		if(httpResponse == 200){
			//load dashboard
		}
		else{
			Window.alert("Username: " + view.getUsername() + "Password: " + view.getPassword());
		}
	}
	
	@Override
	public void onRegisterLinkClicked(){
		eventBus.fireEvent(new GoToRegisterEvent());
	}
}

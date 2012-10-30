package com.cs410.getfit.client.presenter;

import com.cs410.getfit.client.event.GoToDashboardEvent;
import com.cs410.getfit.client.view.LoginView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

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
		//JsonData loginData = new JsonData();
		//int httpResponse = loginData.loginRequest(view.getUsername(), view.getPassword());
		int httpResponse = 200;
		if(httpResponse == 200){
			eventBus.fireEvent(new GoToDashboardEvent());
		}
		else{
			Window.alert("Username: " + view.getUsername() + "Password: " + view.getPassword());
		}
	}

}

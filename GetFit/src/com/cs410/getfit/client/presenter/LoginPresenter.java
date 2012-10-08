package com.cs410.getfit.client.presenter;

import com.cs410.getfit.client.LoginView;
import com.cs410.getfit.client.event.GoToRegisterEvent;
import com.cs410.getfit.client.event.LoginEvent;
import com.cs410.getfit.client.event.RegisterEvent;
import com.google.gwt.event.shared.HandlerManager;
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
	public void onLoginButtonClicked(){
		eventBus.fireEvent(new LoginEvent());
	}
	
	@Override
	public void onRegisterLinkClicked(){
		eventBus.fireEvent(new GoToRegisterEvent());
	}
}

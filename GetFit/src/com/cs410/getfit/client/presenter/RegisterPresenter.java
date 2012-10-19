package com.cs410.getfit.client.presenter;

import com.cs410.getfit.client.event.CancelRegisterEvent;
import com.cs410.getfit.client.event.RegisterEvent;
import com.cs410.getfit.client.view.RegisterView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class RegisterPresenter implements Presenter, RegisterView.Presenter{
	
	private final HandlerManager eventBus;
	private final RegisterView view;
	
	public RegisterPresenter(HandlerManager eventBus, RegisterView view){
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
	public void onRegisterButtonClicked() {
		// TODO: create new user implementation
		eventBus.fireEvent(new RegisterEvent());
	}

	@Override
	public void onCancelRegisterButtonClicked() {
		eventBus.fireEvent(new CancelRegisterEvent());
	}
	
}

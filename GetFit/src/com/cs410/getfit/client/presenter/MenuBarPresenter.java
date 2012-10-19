package com.cs410.getfit.client.presenter;

import com.cs410.getfit.client.view.MenuBarView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class MenuBarPresenter implements Presenter, MenuBarView.Presenter{

	private final HandlerManager eventBus;
	private final MenuBarView view;
	
	public MenuBarPresenter(HandlerManager eventBus, MenuBarView view){
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
	public void onHomeOptionClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChallengesOptionClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProfileOptionClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSettingsOptionClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLogoutOptionClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSearchOptionClicked() {
		// TODO Auto-generated method stub
		
	}
	
}

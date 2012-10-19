package com.cs410.getfit.client.presenter;

import com.cs410.getfit.client.view.DashboardView;
import com.google.gwt.event.shared.HandlerManager;
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
	}
	
}

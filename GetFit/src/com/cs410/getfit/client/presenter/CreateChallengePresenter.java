package com.cs410.getfit.client.presenter;

import com.cs410.getfit.client.view.CreateChallengeView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class CreateChallengePresenter implements Presenter, CreateChallengeView.Presenter{

	private final HandlerManager eventBus;
	private final CreateChallengeView view;
	
	public CreateChallengePresenter(HandlerManager eventBus, CreateChallengeView view){
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

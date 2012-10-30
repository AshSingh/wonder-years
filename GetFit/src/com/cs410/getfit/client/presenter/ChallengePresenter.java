package com.cs410.getfit.client.presenter;

import com.cs410.getfit.client.view.ChallengeView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class ChallengePresenter implements Presenter, ChallengeView.Presenter{

	private final HandlerManager eventBus;
	private final ChallengeView view;
	
	public ChallengePresenter(HandlerManager eventBus, ChallengeView view){
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

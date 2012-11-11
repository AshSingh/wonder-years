package com.cs410.getfit.client.view;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public interface DashboardView {
	
	public interface Presenter {
		void onNewChallengeButtonClicked();
	}
	
	void setPresenter(Presenter presenter);
	void setMenuBar(MenuBarView menuBar);
	MenuBarView getMenuBar();
	Widget asWidget();
	VerticalPanel getNewsFeedPanel();
	Label getNameLabel();
	VerticalPanel getUserChallengesPanel();
}

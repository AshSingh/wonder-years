package com.cs410.getfit.client.view;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public interface ChallengesView {
	
	public interface Presenter {
		void onNewChallengeButtonClicked();
	}
	
	void setPresenter(Presenter presenter);
	void setMenuBar(MenuBarView menuBar);
	MenuBarView getMenuBar();
	Widget asWidget();
	VerticalPanel getUserChallengesPanel();
	VerticalPanel getChallengesPanel();
}

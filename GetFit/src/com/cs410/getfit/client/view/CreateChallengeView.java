package com.cs410.getfit.client.view;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public interface CreateChallengeView {
	
	public interface Presenter {
		void onCreateChallengeButtonClicked();
	}
	
	void setPresenter(Presenter presenter);
	void setMenuBar(MenuBarView menuBar);
	MenuBarView getMenuBar();
	Widget asWidget();
	String getChallengeName();
	Label getChallengeNameLabel();
	String getLocation();
	String getDescription();
	Label getDescriptionLabel();
	boolean getIsPrivate();
}

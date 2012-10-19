package com.cs410.getfit.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface MenuBarView {
	
	public interface Presenter {
	    void onHomeOptionClicked();
	    void onChallengesOptionClicked();
	    void onProfileOptionClicked();
	    void onSettingsOptionClicked();
	    void onLogoutOptionClicked();
	    void onSearchOptionClicked();
	}
	
	void setPresenter(Presenter presenter);
	Widget asWidget();
}

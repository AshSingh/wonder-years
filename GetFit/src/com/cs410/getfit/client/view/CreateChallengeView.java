package com.cs410.getfit.client.view;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public interface CreateChallengeView {
	
	public interface Presenter {
		void onAddActivityButtonClicked(String activity);
	}
	
	void setPresenter(Presenter presenter);
	void setMenuBar(MenuBarView menuBar);
	VerticalPanel getActivitiesPanel();
	MenuBarView getMenuBar();
	Widget asWidget();
	String getDateFormat();
}

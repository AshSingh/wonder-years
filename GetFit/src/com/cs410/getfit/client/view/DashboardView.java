package com.cs410.getfit.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface DashboardView {
	
	public interface Presenter {

	}
	
	void setPresenter(Presenter presenter);
	void setMenuBar(MenuBarView menuBar);
	MenuBarView getMenuBar();
	Widget asWidget();
}

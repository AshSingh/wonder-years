package com.cs410.getfit.client.view;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public interface ErrorView {
	
	public interface Presenter {
		void go(HasWidgets container, String challengeUri);
	}
	
	void setPresenter(Presenter presenter);
	void setMenuBar(MenuBarView menuBar);
	MenuBarView getMenuBar();
	Widget asWidget();
	Label getErrorLabel();
}

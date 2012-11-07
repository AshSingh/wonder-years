package com.cs410.getfit.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface MenuBarView {
	
	public interface Presenter {
	    void onSearchOptionClicked();
	}
	
	void setPresenter(Presenter presenter);
	Widget asWidget();
}

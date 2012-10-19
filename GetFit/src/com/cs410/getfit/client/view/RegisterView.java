package com.cs410.getfit.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface RegisterView {
	
	public interface Presenter {
	    void onRegisterButtonClicked();
	    void onCancelRegisterButtonClicked();
	  }
	
	void setPresenter(Presenter presenter);
	Widget asWidget();
}

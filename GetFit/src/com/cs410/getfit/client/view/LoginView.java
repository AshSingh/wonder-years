package com.cs410.getfit.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface LoginView {
	
	public interface Presenter {
	    void onLoginButtonClicked();
	    void onRegisterLinkClicked();
	  }
	
	void setPresenter(Presenter presenter);
	String getUsername();
	String getPassword();
	Widget asWidget();
}

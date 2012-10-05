package com.cs410.getfit.client;

public interface LoginViewInterface {
	
	public interface Presenter {
	    void onLoginButtonClicked();
	  }
	
	void setPresenter(Presenter presenter);

}

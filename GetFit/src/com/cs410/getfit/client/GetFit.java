package com.cs410.getfit.client;

import com.cs410.getfit.client.presenter.LoginPresenter;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

public class GetFit implements EntryPoint{
	 
	@Override
	public void onModuleLoad() {
		LoginPresenter.exportLoginRequest(); // this will assign the function to a variable in the window object called loginRequest()  
	    HandlerManager eventBus = new HandlerManager(null);
	    AppController appViewer = new AppController(eventBus);
	    appViewer.go(RootPanel.get());
	}
	
}

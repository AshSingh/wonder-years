package com.cs410.getfit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.cs410.getfit.client.AppController;
import com.cs410.getfit.client.view.LoginViewImpl;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.http.client.RequestBuilder;

public class GetFit implements EntryPoint{

	@Override
	public void onModuleLoad() {
		RequestBuilder requestBuilder = null;    
	    HandlerManager eventBus = new HandlerManager(null);
	    AppController appViewer = new AppController(requestBuilder, eventBus);
	    appViewer.go(RootPanel.get());
	}

	
}

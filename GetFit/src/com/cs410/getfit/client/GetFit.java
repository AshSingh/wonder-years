package com.cs410.getfit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.ui.RootPanel;

public class GetFit implements EntryPoint{

	@Override
	public void onModuleLoad() {
		RequestBuilder requestBuilder = null;    
	    HandlerManager eventBus = new HandlerManager(null);
	    AppController appViewer = new AppController(requestBuilder, eventBus);
	    appViewer.go(RootPanel.get());
	}

	
}

package com.cs410.getfit.client;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;


import com.cs410.getfit.client.presenter.LoginPresenter;
import com.cs410.getfit.client.presenter.Presenter;
import com.cs410.getfit.client.event.LoginEvent;
import com.cs410.getfit.client.event.LoginEventHandler;
import com.cs410.getfit.client.LoginView;


public class AppController implements Presenter, ValueChangeHandler<String> {
	
	private final HandlerManager eventBus;
	private final RequestBuilder requestBuilder; 
	private HasWidgets container;
	private LoginView loginView = null;
	
	public AppController(RequestBuilder requestBuilder, HandlerManager eventBus) {
	    this.eventBus = eventBus;
	    this.requestBuilder = requestBuilder;
	    bind();
	  }

	private void bind() {
		History.addValueChangeHandler(this);

	    eventBus.addHandler(LoginEvent.TYPE,
	        new LoginEventHandler() {
	          public void onLogin(LoginEvent event) {
	            doLogin();
	          }
	        });  
		
	}
	
	private void doLogin() {
	    History.newItem("add");
	  }


	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if(loginView == null){
			loginView = new LoginView();
		}
		new LoginPresenter(eventBus, loginView).go(container);
		
	}

	@Override
	public void go(final HasWidgets container) {
		this.container = container;
	    
	    if ("".equals(History.getToken())) {
	      History.newItem("login");
	    }
	    else {
	        History.fireCurrentHistoryState();
	    }
		
	}

}

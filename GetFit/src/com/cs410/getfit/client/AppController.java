package com.cs410.getfit.client;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;


import com.cs410.getfit.client.presenter.LoginPresenter;
import com.cs410.getfit.client.presenter.Presenter;
import com.cs410.getfit.client.presenter.RegisterPresenter;
import com.cs410.getfit.client.event.*;
import com.cs410.getfit.client.LoginViewImpl;


public class AppController implements Presenter, ValueChangeHandler<String> {
	
	private final HandlerManager eventBus;
	private final RequestBuilder requestBuilder; 
	private HasWidgets container;
	
	private LoginViewImpl loginView = null;
	private RegisterViewImpl registerView = null;
	
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

		eventBus.addHandler(GoToRegisterEvent.TYPE,
				new GoToRegisterEventHandler() {
			public void onGoToRegister(GoToRegisterEvent event) {
				doGoToRegister();
			}
		});  
		
		eventBus.addHandler(RegisterEvent.TYPE,
				new RegisterEventHandler() {
			public void onRegister(RegisterEvent event) {
				doRegister();
			}
		});  

		eventBus.addHandler(CancelRegisterEvent.TYPE,
				new CancelRegisterEventHandler() {
			public void onCancelRegister(CancelRegisterEvent event) {
				doCancelRegister();
			}
		});  
	}
	
	private void doLogin() {
	    History.newItem("login");
	}

	private void doGoToRegister() {
	    History.newItem("goToRegister");
	}
	
	private void doRegister() {
	    History.newItem("register");
	}
	
	private void doCancelRegister() {
	    History.newItem("cancelRegister");
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
	    String token = event.getValue();
	    
	    if (token != null) {
	        /*if (token.equals("login")) {
				if(loginView == null){
					loginView = new LoginViewImpl();
				}
				new LoginPresenter(eventBus, loginView).go(container);	 
	        } */   
	        if (token.equals("goToRegister")) {
				if(registerView == null){
					registerView = new RegisterViewImpl();
				}
				new RegisterPresenter(eventBus, registerView).go(container);	 	
	        }
	        else if (token.equals("register")) {
				// TODO: register implementation
	        	// TODO: redirect to dashboard on success
	        }
	        else if (token.equals("cancelRegister")) {
				if(loginView == null){
					loginView = new LoginViewImpl();
				}
				new LoginPresenter(eventBus, loginView).go(container);	 	        	
	        }
	    }		
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

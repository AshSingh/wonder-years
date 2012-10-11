package com.cs410.getfit.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
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
		
		eventBus.addHandler(CancelRegisterEvent.TYPE,
				new CancelRegisterEventHandler() {
			public void onCancelRegister(CancelRegisterEvent event) {
				doCancelRegister();
			}
		});  

		eventBus.addHandler(RegisterEvent.TYPE,
				new RegisterEventHandler() {
			public void onRegister(RegisterEvent event) {
				doRegister();
			}
		}); 
	}
	
	private void doLogin() {
	    History.newItem("login");
	}
	
	private void doGoToRegister() {
	    History.newItem("goToRegister");
	}
	
	private void doCancelRegister() {
		History.newItem("cancelRegister");
	}
	
	private void doRegister() {
		History.newItem("register");
	}

	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
	    String token = event.getValue();
	    
	    if (token != null) {
	        if (token.equals("login") || token.equals("register")) {
				// TODO: go to dashboard view
	        }    
	        else if (token.equals("goToRegister")) {
	        	GWT.runAsync(new RunAsyncCallback() {
	        		public void onFailure(Throwable caught) {
	        		}

	        		public void onSuccess() {
	        			if (registerView == null) {
	        				registerView = new RegisterViewImpl();
	        			}
	        			new RegisterPresenter(eventBus, registerView).go(container);	 	
	        		}
	        	});
	        }
	        else if (token.equals("cancelRegister") || token.equals("goToLogin")) {
	        	GWT.runAsync(new RunAsyncCallback() {
	        		public void onFailure(Throwable caught) {
	        		}

	        		public void onSuccess() {
	        			if (loginView == null) {
	        				loginView = new LoginViewImpl();
	        			}
	        			new LoginPresenter(eventBus, loginView).go(container);	 
	        		}
	        	});
	        }
	    }		
	}

	@Override
	public void go(final HasWidgets container) {
		this.container = container;
	    if ("".equals(History.getToken())) {
	    	History.newItem("goToLogin");
	    }
	    else {
	        History.fireCurrentHistoryState();
	    }	
	}

}

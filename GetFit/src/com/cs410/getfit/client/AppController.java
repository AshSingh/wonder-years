package com.cs410.getfit.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;


import com.cs410.getfit.client.presenter.ChallengePresenter;
import com.cs410.getfit.client.presenter.CreateChallengePresenter;
import com.cs410.getfit.client.presenter.DashboardPresenter;
import com.cs410.getfit.client.presenter.LoginPresenter;
import com.cs410.getfit.client.presenter.MenuBarPresenter;
import com.cs410.getfit.client.presenter.Presenter;
import com.cs410.getfit.client.presenter.RegisterPresenter;
import com.cs410.getfit.client.view.ChallengeViewImpl;
import com.cs410.getfit.client.view.CreateChallengeViewImpl;
import com.cs410.getfit.client.view.DashboardViewImpl;
import com.cs410.getfit.client.view.LoginViewImpl;
import com.cs410.getfit.client.view.MenuBarViewImpl;
import com.cs410.getfit.client.view.RegisterViewImpl;
import com.cs410.getfit.client.event.*;


public class AppController implements Presenter, ValueChangeHandler<String> {
	
	private final HandlerManager eventBus;
	private HasWidgets container;
	
	private LoginViewImpl loginView = null;
	private RegisterViewImpl registerView = null;
	private MenuBarViewImpl menuBarView = null;
	private DashboardViewImpl dashboardView = null;
	private CreateChallengeViewImpl createChallengeView = null;
	private ChallengeViewImpl challengeView = null;	
	
	public AppController(RequestBuilder requestBuilder, HandlerManager eventBus) {
	    this.eventBus = eventBus;
	    bind();
	  }

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(GoToLoginEvent.TYPE,
				new GoToLoginEventHandler() {
			public void onGoToLogin(GoToLoginEvent event) {
				doGoToLogin();
			}
		});  
		
		eventBus.addHandler(GoToRegisterEvent.TYPE,
				new GoToRegisterEventHandler() {
			public void onGoToRegister(GoToRegisterEvent event) {
				doGoToRegister();
			}
		});  
		
		eventBus.addHandler(GoToCreateChallengeEvent.TYPE,
				new GoToCreateChallengeEventHandler() {
			public void onGoToCreateChallenge(GoToCreateChallengeEvent event) {
				doGoToCreateChallenge();
			}
		});  

		eventBus.addHandler(GoToDashboardEvent.TYPE,
				new GoToDashboardEventHandler() {
			public void onGoToDashboard(GoToDashboardEvent event) {
				doGoToDashboard();
			}
		}); 
		
		eventBus.addHandler(GoToChallengeEvent.TYPE,
				new GoToChallengeEventHandler() {
			public void onGoToChallenge(GoToChallengeEvent event) {
				doGoToChallenge();
			}
		});  
	}
	
	private void doGoToLogin() {
	    History.newItem(HistoryValues.LOGIN.toString());
	}
	
	private void doGoToRegister() {
	    History.newItem(HistoryValues.REGISTER.toString());
	}
	
	private void doGoToDashboard() {
		History.newItem(HistoryValues.DASHBOARD.toString());
	}
	
	private void doGoToCreateChallenge() {
		History.newItem(HistoryValues.CREATECHALLENGE.toString());
	}
	
	private void doGoToChallenge() {
		History.newItem(HistoryValues.CHALLENGE.toString());
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
	    String token = event.getValue();
	    
	    if (token != null) {
	        if (token.equals(HistoryValues.DASHBOARD.toString())) {
	        	GWT.runAsync(new RunAsyncCallback() {
	        		public void onFailure(Throwable caught) {
	        		}

	        		public void onSuccess() {
	        			if (dashboardView == null) {
	        				dashboardView = new DashboardViewImpl();
	        			}
	        			if (menuBarView == null) {
	        				menuBarView = new MenuBarViewImpl();
	        			}
	        			dashboardView.setMenuBar(menuBarView);
	        			new MenuBarPresenter(eventBus, menuBarView);
	        			new DashboardPresenter(eventBus, dashboardView).go(container);	 	
	        		}
	        	});
	        }    
	        else if (token.equals(HistoryValues.REGISTER.toString())) {
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
	        else if (token.equals(HistoryValues.LOGIN.toString())) {
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
	        else if (token.equals(HistoryValues.CREATECHALLENGE.toString())) {
	        	GWT.runAsync(new RunAsyncCallback() {
	        		public void onFailure(Throwable caught) {
	        		}

	        		public void onSuccess() {
	        			if (createChallengeView == null) {
	        				createChallengeView = new CreateChallengeViewImpl();
	        			}
	        			if (menuBarView == null) {
	        				menuBarView = new MenuBarViewImpl();
	        			}
	        			createChallengeView.setMenuBar(menuBarView);
	        			new MenuBarPresenter(eventBus, menuBarView);
	        			new CreateChallengePresenter(eventBus, createChallengeView).go(container);	 	
	        		}
	        	});
	        }
	        else if (token.equals(HistoryValues.CHALLENGE.toString())) {
	        	GWT.runAsync(new RunAsyncCallback() {
	        		public void onFailure(Throwable caught) {
	        		}

	        		public void onSuccess() {
	        			if (challengeView == null) {
	        				challengeView = new ChallengeViewImpl();
	        			}
	        			if (menuBarView == null) {
	        				menuBarView = new MenuBarViewImpl();
	        			}
	        			challengeView.setMenuBar(menuBarView);
	        			new MenuBarPresenter(eventBus, menuBarView);
	        			new ChallengePresenter(eventBus, challengeView).go(container);	 	
	        		}
	        	});
	        }
	    }		
	}

	@Override
	public void go(final HasWidgets container) {
		this.container = container;
	    if ("".equals(History.getToken())) {
	    	History.newItem(HistoryValues.LOGIN.toString());
	    }
	    else {
	        History.fireCurrentHistoryState();
	    }	
	}

}

package com.cs410.getfit.client;

import com.cs410.getfit.client.event.GoToChallengeEvent;
import com.cs410.getfit.client.event.GoToChallengeEventHandler;
import com.cs410.getfit.client.event.GoToCreateChallengeEvent;
import com.cs410.getfit.client.event.GoToCreateChallengeEventHandler;
import com.cs410.getfit.client.event.GoToDashboardEvent;
import com.cs410.getfit.client.event.GoToDashboardEventHandler;
import com.cs410.getfit.client.event.GoToEditChallengeEvent;
import com.cs410.getfit.client.event.GoToEditChallengeEventHandler;
import com.cs410.getfit.client.event.GoToErrorEvent;
import com.cs410.getfit.client.event.GoToErrorEventHandler;
import com.cs410.getfit.client.event.GoToLoginEvent;
import com.cs410.getfit.client.event.GoToLoginEventHandler;
import com.cs410.getfit.client.event.GoToRegisterEvent;
import com.cs410.getfit.client.event.GoToRegisterEventHandler;
import com.cs410.getfit.client.presenter.ChallengePresenter;
import com.cs410.getfit.client.presenter.CreateChallengePresenter;
import com.cs410.getfit.client.presenter.DashboardPresenter;
import com.cs410.getfit.client.presenter.EditChallengePresenter;
import com.cs410.getfit.client.presenter.ErrorPresenter;
import com.cs410.getfit.client.presenter.LoginPresenter;
import com.cs410.getfit.client.presenter.MenuBarPresenter;
import com.cs410.getfit.client.presenter.Presenter;
import com.cs410.getfit.client.presenter.RegisterPresenter;
import com.cs410.getfit.client.view.ChallengeViewImpl;
import com.cs410.getfit.client.view.CreateAndEditChallengeViewImpl;
import com.cs410.getfit.client.view.DashboardViewImpl;
import com.cs410.getfit.client.view.ErrorViewImpl;
import com.cs410.getfit.client.view.LoginViewImpl;
import com.cs410.getfit.client.view.MenuBarViewImpl;
import com.cs410.getfit.client.view.RegisterViewImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;


public class AppController implements Presenter, ValueChangeHandler<String> {
	
	private final HandlerManager eventBus;
	private HasWidgets container;
	
	private LoginViewImpl loginView = null;
	private RegisterViewImpl registerView = null;
	private MenuBarViewImpl menuBarView = null;
	private DashboardViewImpl dashboardView = null;
	private CreateAndEditChallengeViewImpl createAndEditChallengeView = null;
	private ChallengeViewImpl challengeView = null;	
	private ErrorViewImpl errorView = null;	
	
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
				doGoToChallenge(event.getChallengeUri());
			}
		});  
		
		eventBus.addHandler(GoToErrorEvent.TYPE,
				new GoToErrorEventHandler() {
			public void onGoToError(GoToErrorEvent event) {
				doGoToError(event.getErrorType());
			}
		}); 
		
		eventBus.addHandler(GoToEditChallengeEvent.TYPE,
				new GoToEditChallengeEventHandler() {
			public void onGoToEditChallenge(GoToEditChallengeEvent event) {
				doGoToEditChallenge(event.getChallengeUri());
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
	
	private void doGoToChallenge(String challengeUri) {
		History.newItem(challengeUri);
	}
	
	private void doGoToError(int errorType) {
		if (errorType != -1) {
			History.newItem(errorType + HistoryValues.ERROR.toString());
		}
		else {
			History.newItem(HistoryValues.ERROR.toString());
		}
	}
	
	private void doGoToEditChallenge(String challengeUri) {
		History.newItem(HistoryValues.EDIT.toString() + challengeUri);
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
	    final String token = event.getValue();
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
	        			if (createAndEditChallengeView == null) {
	        				createAndEditChallengeView = new CreateAndEditChallengeViewImpl();
	        			}
	        			if (menuBarView == null) {
	        				menuBarView = new MenuBarViewImpl();
	        			}
	        			createAndEditChallengeView.setMenuBar(menuBarView);
	        			new MenuBarPresenter(eventBus, menuBarView);
	        			new CreateChallengePresenter(eventBus, createAndEditChallengeView).go(container);	 	
	        		}
	        	});
	        }
	        else if (token.contains("/challenges/") && !token.contains(HistoryValues.EDIT.toString())) {
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
	        			new ChallengePresenter(eventBus, challengeView).go(container, token);	 	
	        		}
	        	});
	        }
	        else if (token.contains(HistoryValues.ERROR.toString())) {
	        	GWT.runAsync(new RunAsyncCallback() {
	        		public void onFailure(Throwable caught) {
	        		}

	        		public void onSuccess() {
	        			if (errorView == null) {
	        				errorView = new ErrorViewImpl();
	        			}
	        			if (menuBarView == null) {
	        				menuBarView = new MenuBarViewImpl();
	        			}
	        			errorView.setMenuBar(menuBarView);
	        			new MenuBarPresenter(eventBus, menuBarView);
	        			new ErrorPresenter(eventBus, errorView).go(container, token);	 	
	        		}
	        	});
	        }
	        else if (token.contains(HistoryValues.EDIT.toString())) {
	        	GWT.runAsync(new RunAsyncCallback() {
	        		public void onFailure(Throwable caught) {
	        		}

	        		public void onSuccess() {
	        			if (createAndEditChallengeView == null) {
	        				createAndEditChallengeView = new CreateAndEditChallengeViewImpl();
	        			}
	        			if (menuBarView == null) {
	        				menuBarView = new MenuBarViewImpl();
	        			}
	        			createAndEditChallengeView.setMenuBar(menuBarView);
	        			new MenuBarPresenter(eventBus, menuBarView);
	        			new EditChallengePresenter(eventBus, createAndEditChallengeView).go(container, token);	 	
	        		}
	        	});
	        }
	        // token doesn't match any of the above
	        else {
	        	eventBus.fireEvent(new GoToErrorEvent());
	        }
	    }
	    // null token - display error page
	    else {
        	eventBus.fireEvent(new GoToErrorEvent());	    	
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

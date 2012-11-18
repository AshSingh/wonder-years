package com.cs410.getfit.client;

import com.cs410.getfit.client.event.GoToChallengeEvent;
import com.cs410.getfit.client.event.GoToChallengeEventHandler;
import com.cs410.getfit.client.event.GoToChallengesEvent;
import com.cs410.getfit.client.event.GoToChallengesEventHandler;
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
import com.cs410.getfit.client.event.GoToUserSettingsEvent;
import com.cs410.getfit.client.event.GoToUserSettingsEventHandler;
import com.cs410.getfit.client.presenter.ChallengePresenter;
import com.cs410.getfit.client.presenter.ChallengesPresenter;
import com.cs410.getfit.client.presenter.CreateChallengePresenter;
import com.cs410.getfit.client.presenter.DashboardPresenter;
import com.cs410.getfit.client.presenter.EditChallengePresenter;
import com.cs410.getfit.client.presenter.ErrorPresenter;
import com.cs410.getfit.client.presenter.LoginPresenter;
import com.cs410.getfit.client.presenter.MenuBarPresenter;
import com.cs410.getfit.client.presenter.Presenter;
import com.cs410.getfit.client.presenter.UserSettingsPresenter;
import com.cs410.getfit.client.view.ChallengeViewImpl;
import com.cs410.getfit.client.view.ChallengesView;
import com.cs410.getfit.client.view.ChallengesViewImpl;
import com.cs410.getfit.client.view.CreateAndEditChallengeViewImpl;
import com.cs410.getfit.client.view.DashboardViewImpl;
import com.cs410.getfit.client.view.ErrorViewImpl;
import com.cs410.getfit.client.view.LoginViewImpl;
import com.cs410.getfit.client.view.MenuBarViewImpl;
import com.cs410.getfit.client.view.UserSettingsView;
import com.cs410.getfit.client.view.UserSettingsViewImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;


public class AppController implements Presenter, ValueChangeHandler<String> {
	
	private final HandlerManager eventBus;
	private HasWidgets container;
	
	private LoginViewImpl loginView = null;
	private MenuBarViewImpl menuBarView = null;
	private DashboardViewImpl dashboardView = null;
	private CreateAndEditChallengeViewImpl createAndEditChallengeView = null;
	private ChallengeViewImpl challengeView = null;	
	private ErrorViewImpl errorView = null;	
	private ChallengesView challengesView = null;
	private UserSettingsView settingsView = null;
	
	/**
	 * Creates AppController to handle the switching of views within the app
	 * 
	 * @param eventBus - manages changing views within the application
	 */
	public AppController(HandlerManager eventBus) {
	    this.eventBus = eventBus;
	    bind();
	  }

	/**
	 * Binds all the possible events sent from the eventBus to the appropriate response method
	 */
	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(GoToLoginEvent.TYPE,
				new GoToLoginEventHandler() {
			public void onGoToLogin(GoToLoginEvent event) {
				doGoToLogin();
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
		
		eventBus.addHandler(GoToChallengesEvent.TYPE,
				new GoToChallengesEventHandler() {
			public void onGoToChallenges(GoToChallengesEvent event) {
				doGoToChallenges();
			}
		});  
		
		eventBus.addHandler(GoToUserSettingsEvent.TYPE,
				new GoToUserSettingsEventHandler() {
			public void onGoToUserSettings(GoToUserSettingsEvent event) {
				doGoToUserSettings();
			}
		}); 
	}
	
	/**
	 * Adds new history item - login
	 * Redirects view to login page
	 */
	private void doGoToLogin() {
	    History.newItem(HistoryValues.LOGIN.toString());
	}
	
	/**
	 * Adds new history item - dashboard
	 * Redirects view to dashboard page
	 */
	private void doGoToDashboard() {
		History.newItem(HistoryValues.DASHBOARD.toString());
	}

	/**
	 * Adds new history item - create challenge
	 * Redirects view to create challenge page
	 */
	private void doGoToCreateChallenge() {
		History.newItem(HistoryValues.CREATECHALLENGE.toString());
	}
	
	/**
	 * Adds new history item - challenge uri
	 * 
	 * @param challengeUri - uri pertaining to a specific challenge
	 */
	private void doGoToChallenge(String challengeUri) {
		History.newItem(challengeUri);
	}
		
	/**
	 * Adds new history item - error
	 * 
	 * @param errorType - possible specific error type
	 */
	private void doGoToError(int errorType) {
		if (errorType != -1) {
			History.newItem(errorType + HistoryValues.ERROR.toString());
		}
		else {
			History.newItem(HistoryValues.ERROR.toString());
		}
	}
	
	/**
	 * Adds new history item - settings
	 * Redirects view to user settings page
	 */
	private void doGoToUserSettings() {
		History.newItem(HistoryValues.SETTINGS.toString());
	}
	
	/**
	 * Adds new history item - edit
	 * Redirects view to edit challenge page
	 * 
	 * @param challengeUri - uri pertaining to a specific challenge
	 */
	private void doGoToEditChallenge(String challengeUri) {
		History.newItem(HistoryValues.EDIT.toString() + challengeUri);
	}
	
	/**
	 * Adds new history item - challenges
	 * Redirects view to challenges page
	 */
	private void doGoToChallenges() {
		History.newItem(HistoryValues.CHALLENGES.toString());
	}
	
	/**
	 * Saves history items onto a stack to allow forward/back browser naviagtion
	 * Also triggered when a new item is added to history
	 * Causes the redirect of views depending on history item
	 * 
	 * @param event - the event that causes views to change
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		DashboardPresenter.cancelRefreshTimer();
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
	        else if (token.equals(HistoryValues.CHALLENGES.toString())) {
	        	GWT.runAsync(new RunAsyncCallback() {
	        		public void onFailure(Throwable caught) {
	        		}

	        		public void onSuccess() {
	        			if (challengesView == null) {
	        				challengesView = new ChallengesViewImpl();
	        			}
	        			if (menuBarView == null) {
	        				menuBarView = new MenuBarViewImpl();
	        			}
	        			challengesView.setMenuBar(menuBarView);
	        			new MenuBarPresenter(eventBus, menuBarView);
	        			new ChallengesPresenter(eventBus, challengesView).go(container);	 	
	        		}
	        	});
	        }
	        else if (token.equals(HistoryValues.SETTINGS.toString())) {
	        	GWT.runAsync(new RunAsyncCallback() {
	        		public void onFailure(Throwable caught) {
	        		}

	        		public void onSuccess() {
	        			if (settingsView == null) {
	        				settingsView = new UserSettingsViewImpl();
	        			}
	        			if (menuBarView == null) {
	        				menuBarView = new MenuBarViewImpl();
	        			}
	        			settingsView.setMenuBar(menuBarView);
	        			new MenuBarPresenter(eventBus, menuBarView);
	        			new UserSettingsPresenter(eventBus, settingsView).go(container);	 	
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

	/**
	 * Allows user to resume session from last view if session is still active
	 * If session is not active or no view history exists, redirect to login page
	 * 
	 * @param container - the root container of the app 
	 */
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

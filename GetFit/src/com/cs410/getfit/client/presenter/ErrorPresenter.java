package com.cs410.getfit.client.presenter;

import com.cs410.getfit.client.event.GoToLoginEvent;
import com.cs410.getfit.client.view.ErrorView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class ErrorPresenter implements Presenter, ErrorView.Presenter{

	private final HandlerManager eventBus;
	private final ErrorView view;

	private final String ERROR_MSG_DEFAULT = "Error - Oh no! An unexpected error has occurred.";
	private final String ERROR_MSG_404 = "Error - 404: The page you are looking for cannot be found.";
	private final String ERROR_MSG_500 = "Error - 500: Internal Server Error";

	/**
	 * Constructor for presenter for error page
	 * 
	 * @param eventBus - manages changing views within the application
	 * @param view - the view to display
	 */	
	public ErrorPresenter(HandlerManager eventBus, ErrorView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	/**
	 * Standard method for displaying the page 
	 * Displays a generic error page 
	 * 
	 * @param container - the root container of the app         
	 */	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
		container.add(view.asWidget());
		// error messgae
		view.getErrorLabel().setText(ERROR_MSG_DEFAULT);
	}

	/**
	 * Standard method for displaying the page 
	 * Displays a specific error page 
	 * 
	 * @param container - the root container of the app         
	 */	
	public void go(HasWidgets container, String errorType) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
		container.add(view.asWidget());
		if (errorType.contains("404")) {
			view.getErrorLabel().setText(ERROR_MSG_404);
		}
		else if (errorType.contains("500")) {
			view.getErrorLabel().setText(ERROR_MSG_500);
		}
		// 403 error thrown when auth token has expired or user is not logged in
		else if (errorType.contains("403")) {
			// redirect to login page
			eventBus.fireEvent(new GoToLoginEvent());
		}
		else {
			view.getErrorLabel().setText(ERROR_MSG_DEFAULT);
		}

	}
	
}

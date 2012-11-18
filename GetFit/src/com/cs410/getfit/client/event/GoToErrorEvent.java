package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToErrorEvent  extends GwtEvent<GoToErrorEventHandler>{
	public static Type<GoToErrorEventHandler> TYPE = new Type<GoToErrorEventHandler>();
	private int errorType;
	
	/**
	 * Constructor for a new event to redirect view to a challenge page
	 * No params will display a generic error page
	 */
	public GoToErrorEvent(){
		errorType = -1;
	}

	/**
	 * Constructor for a new event to redirect view to a challenge page
	 * Param will customize error page to display a specific error
	 * 
	 * @param errorType - specific error type 
	 */
	public GoToErrorEvent(int errorType){
		this.errorType = errorType;
	}
	
	/**
	 * Get the error type from the event
	 * 
	 * @return error type
	 */
	public int getErrorType(){
		return errorType;
	}
	
	/**
	 * Get the type of the event to find associated handlers
	 * 
	 * @return the type of the event
	 */
	@Override
	public Type<GoToErrorEventHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * Invoked by handler to call appropriate redirect method
	 * 
	 * @param handler - event handler for the event
	 */
	@Override
	protected void dispatch(GoToErrorEventHandler handler) {
		handler.onGoToError(this);
	}
}

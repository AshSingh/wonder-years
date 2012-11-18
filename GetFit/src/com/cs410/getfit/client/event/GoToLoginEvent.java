package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToLoginEvent extends GwtEvent<GoToLoginEventHandler>{
	public static Type<GoToLoginEventHandler> TYPE = new Type<GoToLoginEventHandler>();
	
	/**
	 * Get the type of the event to find associated handlers
	 * 
	 * @return the type of the event
	 */
	@Override
	public Type<GoToLoginEventHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * Invoked by handler to call appropriate redirect method
	 * 
	 * @param handler - event handler for the event
	 */
	@Override
	protected void dispatch(GoToLoginEventHandler handler) {
		handler.onGoToLogin(this);
	}

}

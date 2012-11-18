package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToCreateChallengeEvent extends GwtEvent<GoToCreateChallengeEventHandler> {
	public static Type<GoToCreateChallengeEventHandler> TYPE = new Type<GoToCreateChallengeEventHandler>();
	
	/**
	 * Get the type of the event to find associated handlers
	 * 
	 * @return the type of the event
	 */
	@Override
	public Type<GoToCreateChallengeEventHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * Invoked by handler to call appropriate redirect method
	 * 
	 * @param handler - event handler for the event
	 */
	@Override
	protected void dispatch(GoToCreateChallengeEventHandler handler) {
		handler.onGoToCreateChallenge(this);
	}

}

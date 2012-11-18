package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToChallengesEvent extends GwtEvent<GoToChallengesEventHandler>{
	public static Type<GoToChallengesEventHandler> TYPE = new Type<GoToChallengesEventHandler>();
	
	/**
	 * Get the type of the event to find associated handlers
	 * 
	 * @return the type of the event
	 */
	@Override
	public Type<GoToChallengesEventHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * Invoked by handler to call appropriate redirect method
	 * 
	 * @param handler - event handler for the event
	 */
	@Override
	protected void dispatch(GoToChallengesEventHandler handler) {
		handler.onGoToChallenges(this);
	}

}

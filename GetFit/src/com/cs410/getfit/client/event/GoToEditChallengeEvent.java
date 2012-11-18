package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToEditChallengeEvent extends GwtEvent<GoToEditChallengeEventHandler> {
	public static Type<GoToEditChallengeEventHandler> TYPE = new Type<GoToEditChallengeEventHandler>();
	private String challengeUri;
	
	/**
	 *  Will result in displaying an  error page when constructor is called without 
	 *  the id of the challenge to be displayed
	 */
	public GoToEditChallengeEvent(){
		challengeUri = null;
	}
	
	/**
	 * Constructor for a new event to redirect view to a challenge page
	 * 
	 * @param challengeUri - uri pertaining to a specific challenge
	 */
	public GoToEditChallengeEvent(String challengeUri){
		this.challengeUri = challengeUri;
	}
	
	/**
	 * Get the challenge uri from the event
	 * 
	 * @return challenge uri pertaining to a specific challenge
	 */
	public String getChallengeUri() {
		return challengeUri;
	}
	
	/**
	 * Get the type of the event to find associated handlers
	 * 
	 * @return the type of the event
	 */
	@Override
	public Type<GoToEditChallengeEventHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * Invoked by handler to call appropriate redirect method
	 * 
	 * @param handler - event handler for the event
	 */
	@Override
	protected void dispatch(GoToEditChallengeEventHandler handler) {
		handler.onGoToEditChallenge(this);
	}

}

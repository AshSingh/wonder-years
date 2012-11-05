package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToChallengeEvent extends GwtEvent<GoToChallengeEventHandler> {
	public static Type<GoToChallengeEventHandler> TYPE = new Type<GoToChallengeEventHandler>();
	private String challengeUri;

	// will display error page when constructor is called without the id of the challenge to be displayed
	public GoToChallengeEvent(){
		challengeUri = null;
	}
	
	public GoToChallengeEvent(String challengeUri){
		this.challengeUri = challengeUri;
	}
	
	public String getChallengeUri() {
		return challengeUri;
	}
	
	@Override
	public Type<GoToChallengeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GoToChallengeEventHandler handler) {
		handler.onGoToChallenge(this);
	}

}

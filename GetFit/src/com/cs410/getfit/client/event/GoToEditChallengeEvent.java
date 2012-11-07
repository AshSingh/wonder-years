package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToEditChallengeEvent extends GwtEvent<GoToEditChallengeEventHandler> {
	public static Type<GoToEditChallengeEventHandler> TYPE = new Type<GoToEditChallengeEventHandler>();
	private String challengeUri;
	
	// will display error page when constructor is called without the id of the challenge to be displayed
	public GoToEditChallengeEvent(){
		challengeUri = null;
	}
	
	public GoToEditChallengeEvent(String challengeUri){
		this.challengeUri = challengeUri;
	}
	
	public String getChallengeUri() {
		return challengeUri;
	}
	
	@Override
	public Type<GoToEditChallengeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GoToEditChallengeEventHandler handler) {
		handler.onGoToEditChallenge(this);
	}

}

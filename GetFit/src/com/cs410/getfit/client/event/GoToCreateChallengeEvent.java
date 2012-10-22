package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToCreateChallengeEvent extends GwtEvent<GoToCreateChallengeEventHandler> {
	public static Type<GoToCreateChallengeEventHandler> TYPE = new Type<GoToCreateChallengeEventHandler>();
	
	@Override
	public Type<GoToCreateChallengeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GoToCreateChallengeEventHandler handler) {
		handler.onGoToCreateChallenge(this);
	}

}

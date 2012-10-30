package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToChallengeEvent extends GwtEvent<GoToChallengeEventHandler> {
	public static Type<GoToChallengeEventHandler> TYPE = new Type<GoToChallengeEventHandler>();
	
	@Override
	public Type<GoToChallengeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GoToChallengeEventHandler handler) {
		handler.onGoToChallenge(this);
	}

}

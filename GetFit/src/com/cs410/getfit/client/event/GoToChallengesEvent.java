package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToChallengesEvent extends GwtEvent<GoToChallengesEventHandler>{
	public static Type<GoToChallengesEventHandler> TYPE = new Type<GoToChallengesEventHandler>();
	
	@Override
	public Type<GoToChallengesEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GoToChallengesEventHandler handler) {
		handler.onGoToChallenges(this);
	}

}

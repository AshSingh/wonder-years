package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToLoginEvent extends GwtEvent<GoToLoginEventHandler>{
	public static Type<GoToLoginEventHandler> TYPE = new Type<GoToLoginEventHandler>();
	
	@Override
	public Type<GoToLoginEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GoToLoginEventHandler handler) {
		handler.onGoToLogin(this);
	}

}

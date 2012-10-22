package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToRegisterEvent extends GwtEvent<GoToRegisterEventHandler>{
	public static Type<GoToRegisterEventHandler> TYPE = new Type<GoToRegisterEventHandler>();
	
	@Override
	public Type<GoToRegisterEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GoToRegisterEventHandler handler) {
		handler.onGoToRegister(this);
	}

}

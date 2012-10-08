package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class CancelRegisterEvent extends GwtEvent<CancelRegisterEventHandler>{
	public static Type<CancelRegisterEventHandler> TYPE = new Type<CancelRegisterEventHandler>();

	@Override
	public Type<CancelRegisterEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CancelRegisterEventHandler handler) {
		handler.onCancelRegister(this);
	}

}

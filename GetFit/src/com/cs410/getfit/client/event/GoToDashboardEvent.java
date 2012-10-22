package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToDashboardEvent extends GwtEvent<GoToDashboardEventHandler>{
	public static Type<GoToDashboardEventHandler> TYPE = new Type<GoToDashboardEventHandler>();
	
	@Override
	public Type<GoToDashboardEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GoToDashboardEventHandler handler) {
		handler.onGoToDashboard(this);
	}

}

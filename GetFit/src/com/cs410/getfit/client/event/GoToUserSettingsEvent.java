package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToUserSettingsEvent extends GwtEvent<GoToUserSettingsEventHandler> {
	public static Type<GoToUserSettingsEventHandler> TYPE = new Type<GoToUserSettingsEventHandler>();
	
	@Override
	public Type<GoToUserSettingsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GoToUserSettingsEventHandler handler) {
		handler.onGoToUserSettings(this);
	}

}

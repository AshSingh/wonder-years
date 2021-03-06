package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToUserSettingsEvent extends GwtEvent<GoToUserSettingsEventHandler> {
	public static Type<GoToUserSettingsEventHandler> TYPE = new Type<GoToUserSettingsEventHandler>();
	
	/**
	 * Get the type of the event to find associated handlers
	 * 
	 * @return the type of the event
	 */
	@Override
	public Type<GoToUserSettingsEventHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * Invoked by handler to call appropriate redirect method
	 * 
	 * @param handler - event handler for the event
	 */
	@Override
	protected void dispatch(GoToUserSettingsEventHandler handler) {
		handler.onGoToUserSettings(this);
	}

}

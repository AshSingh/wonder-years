package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LoginEventHandler extends EventHandler{
	
	void onLogin(LoginEvent event);
}

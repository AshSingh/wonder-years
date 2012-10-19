package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.cs410.getfit.client.event.RegisterEventHandler;
import com.cs410.getfit.client.view.RegisterViewImpl;

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

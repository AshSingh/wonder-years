package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.cs410.getfit.client.RegisterViewImpl;
import com.cs410.getfit.client.event.RegisterEventHandler;

public class RegisterEvent extends GwtEvent<RegisterEventHandler>{
	public static Type<RegisterEventHandler> TYPE = new Type<RegisterEventHandler>();
	
	@Override
	public Type<RegisterEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RegisterEventHandler handler) {
		handler.onRegister(this);
	}

}

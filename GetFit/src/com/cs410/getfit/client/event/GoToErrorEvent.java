package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoToErrorEvent  extends GwtEvent<GoToErrorEventHandler>{
	public static Type<GoToErrorEventHandler> TYPE = new Type<GoToErrorEventHandler>();
	private int errorType;
	
	public GoToErrorEvent(){
		errorType = -1;
	}
	
	public GoToErrorEvent(int errorType){
		this.errorType = errorType;
	}
	
	public int getErrorType(){
		return errorType;
	}
	
	@Override
	public Type<GoToErrorEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GoToErrorEventHandler handler) {
		handler.onGoToError(this);
	}
}

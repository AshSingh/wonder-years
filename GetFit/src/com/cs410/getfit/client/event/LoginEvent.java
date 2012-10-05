package com.cs410.getfit.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.cs410.getfit.client.LoginView;
import com.cs410.getfit.client.event.LoginEventHandler;
import com.cs410.getfit.server.LoginServlet;
import com.cs410.getfit.server.UsersJSONStringFormat;
import com.cs410.getfit.server.UsersServlet;

public class LoginEvent extends GwtEvent<LoginEventHandler>{
	public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
	
	String username = LoginView.getTempUserName();
	String pass = LoginView.getTempPass();
	
	public LoginEvent(){
		
		try {
            RequestBuilder rb = new RequestBuilder (RequestBuilder.POST, "/login");
            
            rb.setHeader("Content-Type", "application/x-www-form-urlencoded");
            String body = "{\"username\":\""+username+"\",\"password\":\""+pass+"\"}";
            
            Request response = rb.sendRequest(body,  new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    Window.alert("Success" + response.getText());
                }
                @Override
                public void onError(Request request, Throwable exception) {
                    Window.alert("Error occurred" + exception.getMessage());
                }
            });        
		} 
		catch (RequestException e) {
			// TODO catch 403 Forbidden
			e.printStackTrace();
		}
			
		}

	@Override
	public Type<LoginEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoginEventHandler handler) {
		handler.onLogin(this);
		
	}

}

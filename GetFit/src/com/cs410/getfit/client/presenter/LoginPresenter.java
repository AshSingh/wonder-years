package com.cs410.getfit.client.presenter;

import com.cs410.getfit.client.LoginView;
import com.cs410.getfit.client.event.GoToRegisterEvent;
import com.cs410.getfit.client.event.LoginEvent;
import com.cs410.getfit.client.event.RegisterEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class LoginPresenter implements Presenter, LoginView.Presenter{

	private final HandlerManager eventBus;
	private final LoginView view;
	
	public LoginPresenter(HandlerManager eventBus, LoginView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
	    container.add(view.asWidget());
	}
	
	@Override
	public void onLoginButtonClicked(){
		try {
            RequestBuilder rb = new RequestBuilder (RequestBuilder.POST, "/login");
            
            rb.setHeader("Content-Type", "application/json");
            String body = "{\"users\":["+
        			"{\"username\":\""+view.getUsername()+"\",\"firstname\":\"\",\"lastname\":\"\",\"password\":\""+view.getPassword()+"\"}"+
        			"]}";
            Request response = rb.sendRequest(body,  new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    Window.alert("Success" + response.getText());
                    eventBus.fireEvent(new LoginEvent());
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
	public void onRegisterLinkClicked(){
		eventBus.fireEvent(new GoToRegisterEvent());
	}
}

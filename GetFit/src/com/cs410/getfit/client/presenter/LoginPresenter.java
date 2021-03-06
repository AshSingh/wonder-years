package com.cs410.getfit.client.presenter;

import com.cs410.getfit.client.event.GoToDashboardEvent;
import com.cs410.getfit.client.event.GoToErrorEvent;
import com.cs410.getfit.client.view.LoginView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.HasWidgets;

public class LoginPresenter implements Presenter, LoginView.Presenter{

	private static HandlerManager eventBus;
	private final LoginView view;
	private static int loginStatus = -1;
	private static String getResponseStr = null;
	private static RequestBuilder rb;
	
	/**
	 * Constructor for presenter for login page
	 * 
	 * @param eventBus - manages changing views within the application
	 * @param view - the view to display
	 */
	public LoginPresenter(HandlerManager eventBus, LoginView view){
		LoginPresenter.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	/**
	 * Standard method for displaying the page 
	 * Displays the login page 
	 * 
	 * @param container - the root container of the app         
	 */	
	@Override
	public void go(HasWidgets container) {
		container.clear();
	    container.add(view.asWidget());
	}
	
	public static void loginRequest(String fb_user){
		try{
			rb = new RequestBuilder(RequestBuilder.POST, "/getfit/login");
			
			rb.setHeader("Content-Type", "application/json");
            String body = fb_user;
            Request response = rb.sendRequest(body, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    loginStatus = response.getStatusCode();
                    getResponseStr = response.getText();
                    JSONObject jsonBody = (JSONObject) JSONParser.parse(getResponseStr);
                    
                    if(loginStatus == 200) {
                        String accessToken = fb_getAuthResponse();
            			Cookies.setCookie("accessToken", accessToken);
                        Cookies.setCookie("guid", "" + (long) jsonBody.get("guid").isNumber().doubleValue());
            			eventBus.fireEvent(new GoToDashboardEvent());
            		} else {
        				eventBus.fireEvent(new GoToErrorEvent(response.getStatusCode()));
            		}
                }
                @Override
                public void onError(Request request, Throwable exception) {
    				eventBus.fireEvent(new GoToErrorEvent());
                }
            });
		}
		
		catch(RequestException e){
			eventBus.fireEvent(new GoToErrorEvent());
		}		
	}
	
	public static native void exportLoginRequest() /*-{
		$wnd.loginRequest = $entry(@com.cs410.getfit.client.presenter.LoginPresenter::loginRequest(Ljava/lang/String;));
	}-*/;
	
	/* Javascript
	 * Login button clicked
	 * @see com.cs410.getfit.client.view.LoginView.Presenter#onLoginButtonClicked()
	 */
	@Override
	public native void onLoginButtonClicked() /*-{
		  $wnd.login();
	}-*/;
	
	/**
	 * Javascript Method
	 * facebook request for auth token
	 * @return access token
	 */
	private static native String fb_getAuthResponse() /*-{
		var authResponse = $wnd.FB.getAuthResponse();
		return authResponse.accessToken;
	}-*/;

}

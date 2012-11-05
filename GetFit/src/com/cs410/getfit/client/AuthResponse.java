package com.cs410.getfit.client;


// SINGLETON 
// to handle the access token on the client side
public class AuthResponse {
	private String accessToken;
	private static final AuthResponse instance = new AuthResponse();
	
	private AuthResponse() {
		this.accessToken = fb_getAuthResponse();
	}
	
	public static AuthResponse getInstance() {
		return instance;
	}
	
	private static native String fb_getAuthResponse() /*-{
		var authResponse = $wnd.FB.getAuthResponse();
		return authResponse.accessToken;
	}-*/;
	
	public String toString() {
		return this.accessToken;
	}
}

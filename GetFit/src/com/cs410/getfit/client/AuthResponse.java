package com.cs410.getfit.client;


// SINGLETON 
// to handle the access token on the client side
public class AuthResponse {
	private String accessToken;
	private int guid;
	private static final AuthResponse instance = new AuthResponse();
	
	//gets access token from facebook for the authentication filter
	private AuthResponse() {
		this.accessToken = fb_getAuthResponse();
	}
	
	//returns singleton
	public static AuthResponse getInstance() {
		return instance;
	}
	
	//facebook request for auth token
	private static native String fb_getAuthResponse() /*-{
		var authResponse = $wnd.FB.getAuthResponse();
		return authResponse.accessToken;
	}-*/;
	
	// CALL THIS METHOD FOR THE GUID ON THE CLIENT
	//returns logged in user's guid
	public int getGuid() {
		return this.guid;
	}
	
	// DON'T CALL THIS METHOD PLEASE!
	//used by login presenter to set logged in user's guid
	public void setGuid(int guid) {
		this.guid = guid;
	}
	
	// CALL THIS TO GET THE ACCESS TOKEN BEFORE EVERY REQUEST TO SERVER
	//logged in user's access token
	public String getAccessToken() {
		return this.accessToken;
	}
}

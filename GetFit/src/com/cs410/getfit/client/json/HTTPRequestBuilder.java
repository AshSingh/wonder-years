package com.cs410.getfit.client.json;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Cookies;

public class HTTPRequestBuilder {
	
	private final static String header = "Content-Type";
	private final static String headerValue = "application/json";
	private final static String warFileName = "/getfit";
	
	public static RequestBuilder getPostRequest(String url) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, warFileName + url);
		builder.setHeader(header, headerValue);
		return builder;
	}

	public static RequestBuilder getGetRequest(String url) {
		String query = "access_token=" + URL.encode(Cookies.getCookie("accessToken"));
		if(url.indexOf('?') == -1) {
			url += "?" + query;
		} else {
			url += "&" + query;
		}
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, warFileName + url);
		builder.setHeader(header, headerValue);
		return builder;
	}

	public static RequestBuilder getPutRequest(String url) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.PUT, warFileName + url);
		builder.setHeader(header, headerValue);
		return builder;
	}
}

package com.cs410.getfit.client.json;

import com.google.gwt.http.client.RequestBuilder;

public class HTTPRequestBuilder {
	
	private final static String header = "Content-Type";
	private final static String headerValue = "application/json";
	
	public static RequestBuilder getPostRequest(String url) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		builder.setHeader(header, headerValue);
		return builder;
	}
}

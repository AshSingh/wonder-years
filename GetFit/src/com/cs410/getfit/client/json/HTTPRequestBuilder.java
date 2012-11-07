package com.cs410.getfit.client.json;

import com.google.gwt.http.client.RequestBuilder;

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

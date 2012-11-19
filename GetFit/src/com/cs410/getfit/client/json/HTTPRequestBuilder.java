package com.cs410.getfit.client.json;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Cookies;

public class HTTPRequestBuilder {
	
	private final static String header = "Content-Type";
	private final static String headerValue = "application/json";
	private final static String warFileName = "/getfit";
	
	/**
	 * Creates a request builder for an HTTP POST request
	 * 
	 * @param url - url to send HTTP request
	 * @return a RequestBuidler that can be used to send an HTTP POST request
	 */
	public static RequestBuilder getPostRequest(String url) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, warFileName + url);
		builder.setHeader(header, headerValue);
		return builder;
	}
	public static RequestBuilder getDeleteRequest(String url) {
		String query = "access_token=" + URL.encode(Cookies.getCookie("accessToken"));
		if(url.indexOf('?') == -1) {
			url += "?" + query;
		} else {
			url += "&" + query;
		}
		RequestBuilder builder = new RequestBuilder(RequestBuilder.DELETE, warFileName + url);
		builder.setHeader(header, headerValue);
		return builder;
	}

	/**
	 * Creates a request builder for an HTTP GET request
	 * 
	 * @param url - url to send HTTP request
	 * @return a RequestBuidler that can be used to send an HTTP GET request
	 */
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

	/**
	 * Creates a request builder for an HTTP PUT request
	 * 
	 * @param url - url to send HTTP request
	 * @return a RequestBuidler that can be used to send an HTTP PUT request
	 */
	public static RequestBuilder getPutRequest(String url) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.PUT, warFileName + url);
		builder.setHeader(header, headerValue);
		return builder;
	}
}

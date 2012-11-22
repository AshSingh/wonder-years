package com.cs410.getfit.client.json;

import org.junit.Test;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Cookies;

public class HttpRequestBuilderTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}
	
	String header = "Content-Type";
	String headerValue = "application/json";
	String warFileName = "/getfit";

	String posturl = "/challenges";
	String geturl = "/challenges/2";
	String puturl = "/challenges/1";
	String deleteurl = "/challenges/1/participants/1";
	
	@Test
	public void testGetPostRequest() {
		Cookies.setCookie("accessToken", "r92347");
		RequestBuilder builder = HTTPRequestBuilder.getPostRequest(posturl);
		assertEquals(builder.getHeader(header), headerValue);
		assertEquals(builder.getUrl(), warFileName + posturl);
	}

	@Test
	public void testGetGetRequest() {
		Cookies.setCookie("accessToken", "r92347");
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest(geturl);
		assertEquals(builder.getHeader(header), headerValue);
		assertEquals(builder.getUrl(), warFileName + geturl + "?access_token=r92347");
	}

	@Test
	public void testGetPutRequest() {
		Cookies.setCookie("accessToken", "r92347");
		RequestBuilder builder = HTTPRequestBuilder.getPutRequest(puturl);
		assertEquals(builder.getHeader(header), headerValue);
		assertEquals(builder.getUrl(), warFileName + puturl);
	}

	@Test
	public void testGetDeleteRequest() {
		Cookies.setCookie("accessToken", "r92347");
		RequestBuilder builder = HTTPRequestBuilder.getDeleteRequest(deleteurl);
		assertEquals(builder.getHeader(header), headerValue);
		assertEquals(builder.getUrl(), warFileName + deleteurl + "?access_token=r92347");
	}
}

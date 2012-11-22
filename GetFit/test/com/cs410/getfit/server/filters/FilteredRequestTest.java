package com.cs410.getfit.server.filters;

import static org.junit.Assert.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class FilteredRequestTest {
	
    MockHttpServletRequest request = new MockHttpServletRequest();
	
	FilteredRequest filtered =  new FilteredRequest((ServletRequest) request);
	
	@Test
	public void sanitizeTest() {
		String input = "+-0123456789#&";
		String output = "+-0123456789#";
		String sanitized = filtered.sanitize(input);
		assertEquals(output, sanitized);
	}

	@Test
	public void getParamaterFBTest(){
		String fb_input = "r98753&";
		String fb_output = "98753";
		filtered.setFB_id(fb_input);
		assertEquals(fb_output, filtered.getParameter("FB_id"));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void getParamaterValueTest(){
		assertEquals(null, filtered.getParameterValues("other"));
	}
	
	@Test 
	public void getParameterJsonBodyTest(){
		String json = "{\"completedchallenges\":[{\"info\":" +
				"{\"userId\":2" +
				 "}}," +
				 "{\"info\":" +
							"{\"userId\":1" +
				 "}}], \"accessToken\":\"r109809f\"}";
		filtered.setJson_body(json);
		assertEquals(json, filtered.getParameter("json_body"));
	}
	
	@Test
	public void getParameterOtherTest(){
		assertEquals(null, filtered.getParameter("other"));
	}
}

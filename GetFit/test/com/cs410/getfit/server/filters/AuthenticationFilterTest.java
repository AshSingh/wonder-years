package com.cs410.getfit.server.filters;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class AuthenticationFilterTest {
	private JUnit4Mockery context = new JUnit4Mockery();
	
	@Test
	public void shouldReturnAValidaRedirectionMessage() {
	    MockHttpServletRequest request = new MockHttpServletRequest();
	    MockHttpServletResponse response = new MockHttpServletResponse();
	    
	    FilterChain chain = context.mock(FilterChain.class);

	    AuthenticationFilter filter = new AuthenticationFilter();
	    try {
			filter.doFilter(request, response, chain);
			assertEquals(200, response.getStatus());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void request403Test(){
	    MockHttpServletRequest request = new MockHttpServletRequest();
	    MockHttpServletResponse response = new MockHttpServletResponse();
		AuthenticationFilter filter = new AuthenticationFilter();
		FilterConfig config = context.mock(FilterConfig.class);
		FilterChain chain = context.mock(FilterChain.class);
		try {
			filter.init(config);
			filter.doFilter(request, response, chain);
			assertEquals(403, response.getStatus());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getRequestTest(){
	    MockHttpServletRequest request = new MockHttpServletRequest();
	    request.setMethod("GET");
	    request.setParameter("access_token", "r987234");
	    MockHttpServletResponse response = new MockHttpServletResponse();
		AuthenticationFilter filter = new AuthenticationFilter();
		FilterConfig config = context.mock(FilterConfig.class);
		FilterChain chain = context.mock(FilterChain.class);
		try {
			filter.init(config);
			filter.doFilter(request, response, chain);
			assertEquals(403, response.getStatus());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void postRequestTest(){
		String incomingJson = "{\"completedchallenges\":[{\"info\":" +
				"{\"userId\":2" +
				 "}}," +
				 "{\"info\":" +
							"{\"userId\":1" +
				 "}}], \"accessToken\":\"r109809f\"}";
	    MockHttpServletRequest request = new MockHttpServletRequest();
	    request.setMethod("POST");
	    request.setContent(incomingJson.getBytes());
	    MockHttpServletResponse response = new MockHttpServletResponse();
		AuthenticationFilter filter = new AuthenticationFilter();
		FilterConfig config = context.mock(FilterConfig.class);
		FilterChain chain = context.mock(FilterChain.class);
		try {
			filter.init(config);
			filter.doFilter(request, response, chain);
			assertEquals(403, response.getStatus());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}

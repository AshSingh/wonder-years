package com.cs410.getfit.server.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.springframework.expression.Operation;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.junit.Assert.assertEquals;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

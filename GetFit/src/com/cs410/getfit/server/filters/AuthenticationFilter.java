package com.cs410.getfit.server.filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AuthenticationFilter implements Filter{

	private FilterConfig filterConfig = null;
	
	@Override
	public void destroy() {
		this.filterConfig = null;
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if(filterConfig == null){
			return;
		}
		String token = (String) request.getParameter("FB_Token");
		// Field to be retrieved from facebook
		String fields = "id";
		String FB_url = "https://graph.facebook.com/me";
		
		String query = String.format("fields=%s&access_token=%s",
				URLEncoder.encode(fields, "UTF-8"),
			     URLEncoder.encode(token, "UTF-8"));
	
		String result = null;
		
		try{
			// Send data
			String urlStr = FB_url + "?" + query;
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection ();
	
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			
			//wait for the asynchronous data to be received
			while ((line = rd.readLine()) != null){
				sb.append(line);
			}
			rd.close();
			result = sb.toString();
			System.out.println(result);
			
			//convert JSON to String
			JsonParser parser = new JsonParser();
			JsonObject jObjBody = (JsonObject)parser.parse(result);
			String FB_id = jObjBody.get("id").getAsString();

			FilteredRequest fr = new FilteredRequest(request);
			fr.setFB_id(FB_id);
			chain.doFilter(fr, response);
		} 
		
		catch (Exception e){
			e.printStackTrace();
		}
	}

}

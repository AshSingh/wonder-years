package com.cs410.getfit.server.filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
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
		
		// Cast it to a httpRequest to extract more data.
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		FilteredRequest filtered_request = new FilteredRequest(request);

		
		String requestMethod = httpRequest.getMethod();
		String token = "";
		
		if(requestMethod.equals("GET")) {
			token = (String) request.getParameter("access_token");
		} else if (requestMethod.equals("POST") || requestMethod.equals("PUT")) {
			StringBuffer jb = new StringBuffer();
			String line = null;
			try {
				BufferedReader reader = request.getReader();
			    while ((line = reader.readLine()) != null)
			      jb.append(line);
			  } catch (Exception e) { 
				  e.printStackTrace(); 
			  }

			String body= jb.toString();
			
			System.out.println(body);
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(body);
			if(element.isJsonObject()) {
				JsonObject jObjBody = (JsonObject)element;
				token = jObjBody.get("accessToken").getAsString();
				jObjBody.remove("accessToken"); 				// Remove the accessToken so the Servlets don't worry about it
				filtered_request.setJson_body(jObjBody.toString());
			}
		}
		
		
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

			HttpURLConnection conn = (HttpURLConnection)url.openConnection ();

			if(conn.getResponseCode() == 200) {
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
				
				//convert JSON to String
				JsonParser parser = new JsonParser();
				JsonObject jObjBody = (JsonObject)parser.parse(result);
				if(jObjBody.get("error") == null) {
					// If no error in the FB response get the ID
					String FB_id = jObjBody.get("id").getAsString();
					filtered_request.setFB_id(FB_id);
					// Authorized user call the next filter on chain
					// if there is no filter calls the servlet
					chain.doFilter(filtered_request, response);
				} else {
					// If there is an error in the FB response
					// return Forbidden 403
					httpResponse.setStatus(403);
				}
			} else {
				// Error in the FB response
				// return Forbidden 403
				httpResponse.setStatus(403);
			}
		}
		
		catch (Exception e){
			e.printStackTrace();
		}
	}

}

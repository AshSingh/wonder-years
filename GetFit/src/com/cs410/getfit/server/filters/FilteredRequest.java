package com.cs410.getfit.server.filters;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class FilteredRequest extends HttpServletRequestWrapper {

	//These are the characters allowed by the Javascript Validation
	static String allowedChars = "+-0123456789#*";
	private String FB_id;
	private String json_body;
	
	/**
	 * @param request Client's http request
	 */
	public FilteredRequest(ServletRequest request) {
		super((HttpServletRequest)request);
		this.FB_id = null;
		this.json_body = null;
	}
	
	/**
	 * Validates input
	 * @param input
	 * @return valid result
	 */
	public String sanitize(String input){
		String result = "";
		for(int i = 0; i < input.length(); i++){
			if(allowedChars.indexOf(input.charAt(i)) >= 0){
				result += input.charAt(i);
			}
		}
		return result;
	}
	
	/* Obtain FB_id parameter from message
	 * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
	 */
	public String getParameter (String paramName){
		String value = null;
		if("FB_id".equals(paramName)) {
			value = sanitize(this.FB_id);
		}
		else if("json_body".equals(paramName)) {
			value = this.json_body;
		}
		else {
			value = super.getParameter(paramName);
		}
		return value;
	}
	
	/* Parse access token in message
	 * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
	 */
	public String[] getParameterValues(String paramName){
		String values[] = super.getParameterValues(paramName);
		if("FB_id".equals(paramName)){
			for(int index = 0; index < values.length; index++){
				values[index] = sanitize(values[index]);
			}
		}
		return values;
	}
	
	/**
	 * @param value set FB_id
	 */
	public void setFB_id(String value){
		this.FB_id = value;
	}
	
	/**
	 * @param body set JSON message body
	 */
	public void setJson_body(String body) {
		this.json_body = body;
	}
}

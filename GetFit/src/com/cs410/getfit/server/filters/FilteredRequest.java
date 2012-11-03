package com.cs410.getfit.server.filters;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class FilteredRequest extends HttpServletRequestWrapper {

	//These are the characters allowed by the Javascript Validation
	static String allowedChars = "+-0123456789#*";
	private String FB_id;
	
	public FilteredRequest(ServletRequest request) {
		super((HttpServletRequest)request);
		this.FB_id = null;
	}
	
	public String sanitize(String input){
		String result = "";
		for(int i = 0; i < input.length(); i++){
			if(allowedChars.indexOf(input.charAt(i)) >= 0){
				result += input.charAt(i);
			}
		}
		return result;
	}
	
	public String getParameter (String paramName){
		String value = null;
		if("FB_id".equals(paramName)) {
			value = sanitize(this.FB_id);
		}
		else {
			value = super.getParameter(paramName);
		}
		return value;
	}
	
	public String[] getParameterValues(String paramName){
		String values[] = super.getParameterValues(paramName);
		if("dangerousParamName".equals(paramName)){
			for(int index = 0; index < values.length; index++){
				values[index] = sanitize(values[index]);
			}
		}
		return values;
	}
	
	public void setFB_id(String value){
		this.FB_id = value;
	}
}

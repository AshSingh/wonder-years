package com.cs410.getfit.client;

import java.lang.reflect.Type;

import com.cs410.getfit.client.event.GoToLoginEvent;
import com.cs410.getfit.shared.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

public class JsonData {
	
	private String resource;
	private RequestBuilder rb;
	private int loginStatus = -1;
	private int putStatus = -1;
	private int getStatus = -1;
	private int postStatus = -1;
	private int delStatus = -1;
	private String getResponseStr = null;
	
	
	public JsonData(){
		RequestBuilder rb = null;
		this.rb = rb;
		this.resource = resource;
	}
	
	public int loginRequest(String loginUsername, String loginPassword){
		try{
			resource = "/login";
			rb = new RequestBuilder(RequestBuilder.POST, resource);
			
			rb.setHeader("Content-Type", "application/x-www-form-urlencoded");
            String body = "{\"username\":\""+loginUsername+"\",\"password\":\""+loginPassword+"\"}";
            Request response = rb.sendRequest(body, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    loginStatus = response.getStatusCode();
                }
                @Override
                public void onError(Request request, Throwable exception) {
                    Window.alert("Error occurred" + exception.getMessage());
                    //TODO: Error handling
                }
            });
		}   
		
		catch(RequestException e){
			//TODO: Error handling
			e.printStackTrace();
		}
		return loginStatus;
		
	}
	
	public int putRequest(String resource, String field, String newValue){
		try{
			rb = new RequestBuilder(RequestBuilder.PUT, resource);
			rb.setHeader("Content-Type", "application/x-www-form-urlencoded");
            String body = "{\""+field+"\":\""+newValue+"\"}";
            Request response = rb.sendRequest(body, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    putStatus = response.getStatusCode();
                }
                @Override
                public void onError(Request request, Throwable exception) {
                    Window.alert("Error occurred" + exception.getMessage());
                    //TODO: Incorrect Response
                }
            });
		}   
		
		catch(RequestException e){
			//TODO: How to handle exception for now print stack trace
			e.printStackTrace();
		}
		return putStatus;
	}
	
	public Object getRequest(String resource, Object o){
		Object obj = null;
        Gson JsonObject = new Gson();
        String responseText;
		try{
			rb = new RequestBuilder(RequestBuilder.GET, resource);
			rb.setHeader("Content-Type", "application/x-www-form-urlencoded");
            String body = "";
            Request response = rb.sendRequest(body, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                	if(response.getStatusCode() == 200){
                        getResponseStr = response.getText();
                	}
                }
                @Override
                public void onError(Request request, Throwable exception) {
                    Window.alert("Error occurred" + exception.getMessage());
                    //TODO: Incorrect Response
                }
            });
		}   
		
		catch(RequestException e){
			//TODO: How to handle exception for now print stack trace
			e.printStackTrace();
		}
		if(getResponseStr != null){
			obj = JsonObject.fromJson(getResponseStr, o.getClass());
		}
		return obj;
	}
	
	public int postRequest(String resource, Object postObj){
		try{
			rb = new RequestBuilder(RequestBuilder.POST, resource);
			rb.setHeader("Content-Type", "application/x-www-form-urlencoded");
			Gson JsonObject = new Gson();
			String jsonUser = JsonObject.toJson(postObj);
            Request response = rb.sendRequest(jsonUser, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    postStatus = response.getStatusCode();
                }
                @Override
                public void onError(Request request, Throwable exception) {
                    Window.alert("Error occurred" + exception.getMessage());
                    //TODO: Incorrect Response
                }
            });
		}   
		
		catch(RequestException e){
			//TODO: How to handle exception for now print stack trace
			e.printStackTrace();
		}
		return postStatus;
	}
	
	public int delRequest(String Resource){
		try{
			rb = new RequestBuilder(RequestBuilder.DELETE, resource);
			rb.setHeader("Content-Type", "application/x-www-form-urlencoded");
			String body = "";
            Request response = rb.sendRequest(body, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    delStatus = response.getStatusCode();
                }
                @Override
                public void onError(Request request, Throwable exception) {
                    Window.alert("Error occurred" + exception.getMessage());
                    //TODO: Incorrect Response
                }
            });
		}   
		
		catch(RequestException e){
			//TODO: How to handle exception for now print stack trace
			e.printStackTrace();
		}
		return delStatus;
	}
}

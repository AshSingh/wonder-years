package com.cs410.getfit.server;

import com.google.gson.JsonObject;

public class UsersJSONStringFormat {
	public static final String USERNAME="username";
	public static final String PASSWORD="password";
	public static final String FIRSTNAME = "firstname";
	public static final String LASTNAME = "lastname";
	public static final String USERS = "users";
	public static final String USER = "user";
	
	public static String getUsername(JsonObject jsonObject) {
		return jsonObject.get(USERNAME).toString();
	}
	public static String getPassword(JsonObject jsonObject) {
		return jsonObject.get(PASSWORD).toString();
	}
	public static String getFirstName(JsonObject jsonObject) {
		return jsonObject.get(FIRSTNAME).toString();
	}
	public static String getLastName(JsonObject jsonObject) {
		return jsonObject.get(LASTNAME).toString();
	}
}

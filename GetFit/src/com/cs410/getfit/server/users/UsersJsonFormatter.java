package com.cs410.getfit.server.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cs410.getfit.shared.User;
import com.cs410.getfit.shared.UserImpl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UsersJsonFormatter {
	
	public static String getJSONFormattedStringOfUsers(List<User> users) {
		JsonObject jsonFormattedUsers = new JsonObject();

		JsonArray userObjects = new JsonArray();
		
		for (User user: users) {
			JsonObject userObj = new JsonObject();

			userObj.addProperty(UserJsonFields.USERNAME.toString(), user.getUsername());
			userObj.addProperty(UserJsonFields.FIRSTNAME.toString(), user.getFirstName());
			userObj.addProperty(UserJsonFields.LASTNAME.toString(), user.getLastName());
			userObj.addProperty(UserJsonFields.PASSWORD.toString(), user.getPassword());

			userObjects.add(userObj);	
		}
		jsonFormattedUsers.add(UserJsonFields.USERS.toString(), userObjects);
		return jsonFormattedUsers.toString();
	}
	public static List<User> getUsersFromJSONFormattedString(String jsonFormattedUsersString) {
		JsonObject object = (JsonObject)new JsonParser().parse(jsonFormattedUsersString);
		User[] array = new Gson().fromJson(object.get(UserJsonFields.USERS.toString()), UserImpl[].class);
		return new ArrayList<User>(Arrays.asList(array));
	}
}

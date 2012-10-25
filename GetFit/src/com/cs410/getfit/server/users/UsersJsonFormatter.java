package com.cs410.getfit.server.users;

import java.util.List;

import com.cs410.getfit.server.json.ResourceFormatter;
import com.cs410.getfit.server.models.User;
import com.cs410.getfit.server.models.UserImpl;

public class UsersJsonFormatter extends ResourceFormatter{
	
	public UsersJsonFormatter() {
		super(UserImpl[].class, UserImpl.class, UserJsonFields.USERS.toString());
	}
	
	public List<User> getResourcesFromJSONFormattedString(String jsonFormattedResourceString) {
		return (List<User>)super.getResourcesFromJSONFormattedString(jsonFormattedResourceString);
	}
}

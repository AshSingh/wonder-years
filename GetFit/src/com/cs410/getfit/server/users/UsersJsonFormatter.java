package com.cs410.getfit.server.users;

import java.util.List;

import com.cs410.getfit.server.ResourceJsonFormatter;
import com.cs410.getfit.shared.User;
import com.cs410.getfit.shared.UserImpl;

public class UsersJsonFormatter extends ResourceJsonFormatter{
	
	public UsersJsonFormatter() {
		super(UserImpl.class, UserImpl[].class, UserJsonFields.USERS.toString());
	}
	
	public List<User> getResourcesFromJSONFormattedString(String jsonFormattedResourceString) {
		return (List<User>)super.getResourcesFromJSONFormattedString(jsonFormattedResourceString);
	}
}

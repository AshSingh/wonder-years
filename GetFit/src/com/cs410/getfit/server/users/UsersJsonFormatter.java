package com.cs410.getfit.server.users;

import java.util.List;

import com.cs410.getfit.server.json.ResourceFormatter;
import com.cs410.getfit.server.models.User;
import com.cs410.getfit.server.models.UserImpl;
import com.cs410.getfit.shared.OutgoingUserJsonModel;

public class UsersJsonFormatter extends ResourceFormatter{
	
	public UsersJsonFormatter() {
		super(UserImpl[].class, OutgoingUserJsonModel.class, UserJsonFields.USERS.toString());
	}
	
	public List<User> getResourcesFromJSONFormattedString(String jsonFormattedResourceString) {
		return (List<User>)super.getResourcesFromJSONFormattedString(jsonFormattedResourceString);
	}
	@Override
	public String getJSONFormattedStringOfResource(List<? extends Object> objects) {
		@SuppressWarnings("unchecked")
		List <User> users = (List <User>) objects;
		List<OutgoingUserJsonModel> outgoingModels = JSONModelToUserConverter.convertToOutgoingUserJsonModel(users);
		return super.getJSONFormattedStringOfResource(outgoingModels);
	}
}

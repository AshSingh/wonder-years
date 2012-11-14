package com.cs410.getfit.server.users.json;

import java.util.List;

import com.cs410.getfit.server.json.ResourceFormatter;
import com.cs410.getfit.server.models.User;
import com.cs410.getfit.shared.IncomingUserJsonModel;
import com.cs410.getfit.shared.OutgoingUserJsonModel;

public class UsersJsonFormatter extends ResourceFormatter{
	
	public UsersJsonFormatter() {
		super(IncomingUserJsonModel[].class, OutgoingUserJsonModel.class, "users");
	}
	
	public List<User> getResourcesFromJSONFormattedString(String jsonFormattedResourceString) {
		List<IncomingUserJsonModel> models= (List<IncomingUserJsonModel>)super.getResourcesFromJSONFormattedString(jsonFormattedResourceString);
		return JSONModelToUserConverter.convertToUsers(models);
	}
	@Override
	public String getJSONFormattedStringOfResource(List<? extends Object> objects) {
		@SuppressWarnings("unchecked")
		List <User> users = (List <User>) objects;
		List<OutgoingUserJsonModel> outgoingModels = JSONModelToUserConverter.convertToOutgoingUserJsonModel(users);
		return super.getJSONFormattedStringOfResource(outgoingModels);
	}
}

package com.cs410.getfit.client.json;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.shared.OutgoingUserJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.cs410.getfit.shared.UserInfoJsonModel;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class UsersJsonFormatter {
	public enum UserJsonFields {
		users,
		info,
		firstname,
		lastname,
		isPrivate,
		links;
	}
	
	public static List<OutgoingUserJsonModel> parseUserJsonInfo(String json) {
		List<OutgoingUserJsonModel> models = new ArrayList<OutgoingUserJsonModel>();
		// entire json string
		JSONValue value = JSONParser.parseLenient(json);
		// json string as a value (superclass)
		JSONObject users = value.isObject();
		// json array of challenges
		JSONArray usersArray = users.get(UserJsonFields.users.toString()).isArray();

		for (int i=0; i < usersArray.size(); i++) {
			OutgoingUserJsonModel model = new OutgoingUserJsonModel();
			UserInfoJsonModel infoModel = new UserInfoJsonModel();
			
			// get single challenge from json
			JSONObject user = usersArray.get(i).isObject();
			// get info related to challenge
			JSONObject info = user.get(UserJsonFields.info.toString()).isObject();
			// set infoModel with challenge info
			infoModel.setFirstname(info.get(UserJsonFields.firstname.toString()).isString().stringValue());
			infoModel.setLastname(info.get(UserJsonFields.lastname.toString()).isString().stringValue());
			infoModel.setIsPrivate(info.get(UserJsonFields.isPrivate.toString()).isBoolean().booleanValue());
			
			JSONArray linksArray = user.get(UserJsonFields.links.toString()).isArray(); 
			List<ResourceLink> links = LinksJsonParser.getLinks(linksArray);
			
			model.setInfo(infoModel);
			model.setLinks(links);
			
			models.add(model);
		}
		return models;
	}
}

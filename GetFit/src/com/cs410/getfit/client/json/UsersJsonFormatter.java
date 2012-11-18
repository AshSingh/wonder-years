package com.cs410.getfit.client.json;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.shared.IncomingUserJsonModel;
import com.cs410.getfit.shared.OutgoingUserJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.cs410.getfit.shared.UserInfoJsonModel;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Cookies;

public class UsersJsonFormatter {
	public enum UserJsonFields {
		users,
		info,
		firstname,
		lastname,
		isPrivate,
		links;
	}
	
	/**
	 * Parses json from the server containing user info
	 * 
	 * @param json - json returned from server
	 * @return list of OutgoingUserJsonModel - each model contains info 
	 * for a user and rels for related HTTP requests
	 */
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
	
	/**
	 * Formats json to send to server containing info for users
	 * 
	 * @param models - user(s) to parse into json
	 * @return string of json of user(s) info
	 */
	public static String formatUserJsonInfo(List<IncomingUserJsonModel> models) {
		JSONArray usersJson = new JSONArray();
		// create array of individual challenge json
		for(IncomingUserJsonModel model: models) {
		
			JSONObject userJson = new JSONObject();
			JSONObject info = new JSONObject();
	
			// user setting info
			info.put(UserJsonFields.isPrivate.toString(), JSONBoolean.getInstance(model.getIsPrivate()));
			
			userJson.put(UserJsonFields.info.toString(), info);

			usersJson.set(usersJson.size(), userJson);
		}
		
		// create json with the array of individual challenge json
		JSONObject requestJson = new JSONObject();
		requestJson.put(UserJsonFields.users.toString(), usersJson);
		// Add the accessToken to the json
		requestJson.put("accessToken", new JSONString(Cookies.getCookie("accessToken")));
		
		return requestJson.toString();
	}
}

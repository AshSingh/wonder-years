package com.cs410.getfit.client.json;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.shared.ChallengeInfoJsonModel;
import com.cs410.getfit.shared.IncomingChallengeJsonModel;
import com.cs410.getfit.shared.OutgoingChallengeJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class ChallengesJsonFormatter {
	public enum ChallengeJsonFields {
		CHALLENGES,
		INFO,
		TITLE,
		DESCRIPTION,
		ISPRIVATE,
		LOCATION,
		ADMIN,
		LINKS;

		@Override public String toString() {
			// return lower case string
			return super.toString().toLowerCase();
		}
	}
	
	public static String formatChallengeJsonInfo(List<IncomingChallengeJsonModel> models) {
		JSONArray challengesJson = new JSONArray();
		// create array of individual challenge json
		for(IncomingChallengeJsonModel model: models) {
		
			JSONObject challengeJson = new JSONObject();
			JSONObject info = new JSONObject();
	
			// challenge info
			info.put(ChallengeJsonFields.TITLE.toString(), new JSONString(model.getTitle()));
			info.put(ChallengeJsonFields.DESCRIPTION.toString(), new JSONString(model.getDescription()));
			info.put(ChallengeJsonFields.ISPRIVATE.toString(), JSONBoolean.getInstance(model.getIsprivate()));
			info.put(ChallengeJsonFields.LOCATION.toString(), new JSONString(model.getLocation())); 
			
			challengeJson.put(ChallengeJsonFields.INFO.toString(), info);

			challengeJson.put(ChallengeJsonFields.ADMIN.toString(), new JSONNumber(model.getAdminId()));
			
			challengesJson.set(challengesJson.size(), challengeJson);
		}
		
		// create json with the array of individual challenge json
		JSONObject requestJson = new JSONObject();
		requestJson.put(ChallengeJsonFields.CHALLENGES.toString(), challengesJson);
		
		return requestJson.toString();
	}
	
	public static List<OutgoingChallengeJsonModel> parseChallengeJsonInfo(String json) {
		List<OutgoingChallengeJsonModel> models = new ArrayList<OutgoingChallengeJsonModel>();
		// entire json string
		JSONValue value = JSONParser.parseLenient(json);
		// json string as a value (superclass)
		JSONObject challenges = value.isObject();
		// json array of challenges
		JSONArray challengesArray = challenges.get(ChallengeJsonFields.CHALLENGES.toString()).isArray();

		for (int i=0; i < challengesArray.size(); i++) {
			OutgoingChallengeJsonModel model = new OutgoingChallengeJsonModel();
			ChallengeInfoJsonModel infoModel = new ChallengeInfoJsonModel();
			
			// get single challenge from json
			JSONObject challenge = challengesArray.get(i).isObject();
			// get info related to challenge
			JSONObject info = challenge.get(ChallengeJsonFields.INFO.toString()).isObject();
			// set infoModel with challenge info
			infoModel.setTitle(info.get(ChallengeJsonFields.TITLE.toString()).isString().stringValue());
			infoModel.setLocation(info.get(ChallengeJsonFields.LOCATION.toString()).isString().stringValue());
			infoModel.setIsprivate(info.get(ChallengeJsonFields.ISPRIVATE.toString()).isBoolean().booleanValue());
			infoModel.setDescription(info.get(ChallengeJsonFields.DESCRIPTION.toString()).isString().stringValue());
			
			JSONArray linksArray = challenge.get(ChallengeJsonFields.LINKS.toString()).isArray(); 
			List<ResourceLink> links = LinksJsonParser.getLinks(linksArray);
			
			model.setInfo(infoModel);
			model.setLinks(links);
			
			models.add(model);
		}
		return models;
	}
}

package com.cs410.getfit.client.json;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.server.challenges.json.CompletedChallengeInfoJsonModel;
import com.cs410.getfit.server.challenges.json.IncomingCompletedChallengeJsonModel;
import com.cs410.getfit.server.challenges.json.OutgoingCompletedChallengeJsonModel;
import com.cs410.getfit.server.json.ResourceLink;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class CompletedChallengesJsonFormatter {
	public enum CompletedChallengesJsonFields {
		completedchallenges,
		info,
		userId,
		dateCompleted, 
		links;
	}

	public static String formatCompletedChallengeJsonInfo(List<IncomingCompletedChallengeJsonModel> models) {
		JSONArray completedChallengesJson = new JSONArray();
		// create array of individual challenge json
		for(IncomingCompletedChallengeJsonModel model: models) {
		
			JSONObject challengeJson = new JSONObject();
			JSONObject info = new JSONObject();
	
			// challenge info
			info.put(CompletedChallengesJsonFields.userId.toString(), new JSONNumber(model.getUserId()));
			
			challengeJson.put(CompletedChallengesJsonFields.info.toString(), info);
			
			completedChallengesJson.set(completedChallengesJson.size(), challengeJson);
		}
		
		// create json with the array of individual challenge json
		JSONObject requestJson = new JSONObject();
		requestJson.put(CompletedChallengesJsonFields.completedchallenges.toString(), completedChallengesJson);
		
		return requestJson.toString();
	}
	
	public static List<OutgoingCompletedChallengeJsonModel> parseCompletedChallengesJsonInfo(String json) {
		List<OutgoingCompletedChallengeJsonModel> models = new ArrayList<OutgoingCompletedChallengeJsonModel>();
		// entire json string
		JSONValue value = JSONParser.parseLenient(json);
		// json string as a value (superclass)
		JSONObject completedChallenges = value.isObject();
		// json array of participants
		JSONArray completedChallengesArray = completedChallenges.get(CompletedChallengesJsonFields.completedchallenges.toString()).isArray();

		for (int i=0; i < completedChallengesArray.size(); i++) {
			OutgoingCompletedChallengeJsonModel model = new OutgoingCompletedChallengeJsonModel();
			CompletedChallengeInfoJsonModel infoModel = new CompletedChallengeInfoJsonModel();
			
			// get single challenge from json
			JSONObject completedChallenge = completedChallengesArray.get(i).isObject();
			// get info related to challenge
			JSONObject info = completedChallenge.get(CompletedChallengesJsonFields.info.toString()).isObject();
			// set infoModel with challenge info
			infoModel.setUserId((long) info.get(CompletedChallengesJsonFields.userId.toString()).isNumber().doubleValue());
			infoModel.setDateCompleted((long) info.get(CompletedChallengesJsonFields.dateCompleted.toString()).isNumber().doubleValue());
			
			JSONArray linksArray = completedChallenge.get(CompletedChallengesJsonFields.links.toString()).isArray(); 
			List<ResourceLink> links = LinksJsonParser.getLinks(linksArray);
			
			model.setInfo(infoModel);
			model.setLinks(links);
			
			models.add(model);
		}
		return models;
	}
	
}

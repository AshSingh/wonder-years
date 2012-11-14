package com.cs410.getfit.client.json;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.shared.ChallengeHistoryInfoJsonModel;
import com.cs410.getfit.shared.OutgoingChallengeHistoryJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class ChallengeHistoryJsonFormatter {
	public enum ChallengeHistoryJsonFields {
		user_newsfeed,
		info,
		datemodified,
		newsfeedItemType,
		links;
	}

	public static List<OutgoingChallengeHistoryJsonModel> parseChallengeHistoryJsonInfo(String json) {
		List<OutgoingChallengeHistoryJsonModel> models = new ArrayList<OutgoingChallengeHistoryJsonModel>();
		// entire json string
		if(!json.equals("")) {
			JSONValue value = JSONParser.parseLenient(json);
			// json string as a value (superclass)
			JSONObject challengeHistory = value.isObject();
			// json array of challenges
			JSONArray challengeHistoryArray = challengeHistory.get(ChallengeHistoryJsonFields.user_newsfeed.toString()).isArray();
	
			for (int i=0; i < challengeHistoryArray.size(); i++) {
				OutgoingChallengeHistoryJsonModel model = new OutgoingChallengeHistoryJsonModel();
				ChallengeHistoryInfoJsonModel infoModel = new ChallengeHistoryInfoJsonModel();
				
				// get single challenge from json
				JSONObject user = challengeHistoryArray.get(i).isObject();
				// get info related to challenge
				JSONObject info = user.get(ChallengeHistoryJsonFields.info.toString()).isObject();
				// set infoModel with challenge info
				infoModel.setDatemodified((long) info.get(ChallengeHistoryJsonFields.datemodified.toString()).isNumber().doubleValue());
				infoModel.setNewsfeedItemType(info.get(ChallengeHistoryJsonFields.newsfeedItemType.toString()).isString().stringValue());
				
				JSONArray linksArray = user.get(ChallengeHistoryJsonFields.links.toString()).isArray(); 
				List<ResourceLink> links = LinksJsonParser.getLinks(linksArray);
				
				model.setInfo(infoModel);
				model.setLinks(links);
				
				models.add(model);
			}
		}
		return models;
	}
}

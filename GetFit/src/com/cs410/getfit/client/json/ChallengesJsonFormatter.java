package com.cs410.getfit.client.json;

import java.util.List;

import com.cs410.getfit.server.challenges.json.ChallengeInfoJsonModel;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class ChallengesJsonFormatter {
	public enum ChallengeJsonFields {
		CHALLENGES,
		INFO,
		TITLE,
		STARTDATE,
		ENDDATE,
		ISPRIVATE,
		LOCATION,
		ADMIN;

		@Override public String toString() {
			// return lower case string
			return super.toString().toLowerCase();
		}
	}

	public static String formatChallengeJsonInfo(List<ChallengeInfoJsonModel> models, int admin) {
		JSONArray challengesJson = new JSONArray();
		// create array of individual challenge json
		for(ChallengeInfoJsonModel model: models) {
			JSONObject challengeJson = new JSONObject();
			JSONObject info = new JSONObject();
	
			// challenge info
			info.put(ChallengeJsonFields.TITLE.toString(), new JSONString(model.getTitle()));
			info.put(ChallengeJsonFields.STARTDATE.toString(), new JSONNumber(model.getStartdate()));
			info.put(ChallengeJsonFields.ENDDATE.toString(), new JSONNumber(model.getEnddate()));
			info.put(ChallengeJsonFields.ISPRIVATE.toString(), new JSONString(Boolean.toString(model.getIsprivate())));
			info.put(ChallengeJsonFields.LOCATION.toString(), new JSONString(model.getLocation())); 
			
			challengeJson.put(ChallengeJsonFields.INFO.toString(), info);

			challengeJson.put(ChallengeJsonFields.ADMIN.toString(), new JSONNumber(admin));
			
			challengesJson.set(challengesJson.size(), challengeJson);
		}
		
		// create json with the array of individual challenge json
		JSONObject requestJson = new JSONObject();
		requestJson.put(ChallengeJsonFields.CHALLENGES.toString(), challengesJson);
		
		return requestJson.toString();
	}
}

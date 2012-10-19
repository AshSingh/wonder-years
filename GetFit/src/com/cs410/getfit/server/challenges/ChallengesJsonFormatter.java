package com.cs410.getfit.server.challenges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cs410.getfit.shared.Challenge;
import com.cs410.getfit.shared.ChallengeImpl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ChallengesJsonFormatter {
	
	public static String getJSONFormattedStringOfChallenges(List<Challenge> challenges) {
		JsonObject jsonFormattedChallenges = new JsonObject();

		JsonArray challengeObjects = new JsonArray();
		
		for (Challenge challenge: challenges) {
			JsonObject challengeObj = new JsonObject();

			challengeObj.addProperty(ChallengeJsonFields.GUID.toString(), challenge.getGuid());
			challengeObj.addProperty(ChallengeJsonFields.TITLE.toString(), challenge.getTitle());
			challengeObj.addProperty(ChallengeJsonFields.STARTDATE.toString(), challenge.getStartDate());
			challengeObj.addProperty(ChallengeJsonFields.ENDDATE.toString(), challenge.getEndDate());
			challengeObj.addProperty(ChallengeJsonFields.LOCATION.toString(), challenge.getLocation());
			challengeObj.addProperty(ChallengeJsonFields.ISPRIVATE.toString(), challenge.isPrivate());

			challengeObjects.add(challengeObj);	
		}
		jsonFormattedChallenges.add(ChallengeJsonFields.CHALLENGES.toString(), challengeObjects);
		System.out.println(jsonFormattedChallenges.toString());
		return jsonFormattedChallenges.toString();
	}

	public static List<Challenge> getChallengesFromJSONFormattedString(String jsonFormattedChallengesString) {
		JsonObject object = (JsonObject)new JsonParser().parse(jsonFormattedChallengesString);
		Challenge[] array = new Gson().fromJson(object.get(ChallengeJsonFields.CHALLENGES.toString()), ChallengeImpl[].class);
		return new ArrayList<Challenge>(Arrays.asList(array));
	}
}

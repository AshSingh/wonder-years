package com.cs410.getfit.server.challenges;

import java.lang.reflect.Type;
import java.util.List;

import com.cs410.getfit.server.ResourceJsonFormatter;
import com.cs410.getfit.shared.Challenge;
import com.cs410.getfit.shared.ChallengeImpl;

public class ChallengesJsonFormatter extends ResourceJsonFormatter{
	static Type clazz = ChallengeImpl.class;
	static Type clazzArray = ChallengeImpl[].class;
	
	public ChallengesJsonFormatter () {
		super(clazz, clazzArray, ChallengeJsonFields.CHALLENGES.toString());
	}

	public List<Challenge> getResourcesFromJSONFormattedString(String jsonFormattedResourceString) {
		return (List<Challenge>) super.getResourcesFromJSONFormattedString(jsonFormattedResourceString);
	}

}

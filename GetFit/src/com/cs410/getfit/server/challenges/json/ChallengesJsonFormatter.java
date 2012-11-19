package com.cs410.getfit.server.challenges.json;

import java.lang.reflect.Type;
import java.util.List;

import com.cs410.getfit.server.json.ResourceFormatter;
import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.shared.IncomingChallengeJsonModel;
import com.cs410.getfit.shared.OutgoingChallengeJsonModel;
/**
 * Contains the functionality to parse between json and challenge objects
 * @author kiramccoan
 *
 */
public class ChallengesJsonFormatter extends ResourceFormatter{
	static Type iclazzArray = IncomingChallengeJsonModel[].class;
	static Type oclazz = OutgoingChallengeJsonModel.class;
	
	public ChallengesJsonFormatter () {
		super(iclazzArray, oclazz, "challenges");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Challenge> getResourcesFromJSONFormattedString(String jsonFormattedResourceString) {
		List<IncomingChallengeJsonModel> jsonModels = (List<IncomingChallengeJsonModel>) super.getResourcesFromJSONFormattedString(jsonFormattedResourceString);
		return JSONModelToChallengeConverter.convertToChallenges(jsonModels);
	}
	@Override
	public String getJSONFormattedStringOfResource(List<? extends Object> objects) {
		@SuppressWarnings("unchecked")
		List <Challenge> challenges = (List <Challenge>) objects;
		List<OutgoingChallengeJsonModel> outgoingModels = JSONModelToChallengeConverter.convertToOutgoingChallengeJsonModel(challenges);
		return super.getJSONFormattedStringOfResource(outgoingModels);
	}

}

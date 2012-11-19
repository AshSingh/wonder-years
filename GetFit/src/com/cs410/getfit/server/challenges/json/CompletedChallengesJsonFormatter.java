package com.cs410.getfit.server.challenges.json;

import java.lang.reflect.Type;
import java.util.List;

import com.cs410.getfit.server.json.ResourceFormatter;
import com.cs410.getfit.server.models.CompletedChallenge;
import com.cs410.getfit.shared.IncomingCompletedChallengeJsonModel;
import com.cs410.getfit.shared.OutgoingCompletedChallengeJsonModel;
/**
 * Contains the functionality to parse between json and completed challenge objects
 * @author kiramccoan
 *
 */
public class CompletedChallengesJsonFormatter extends ResourceFormatter{
	static Type iclazzArray = IncomingCompletedChallengeJsonModel[].class;
	static Type oclazz = OutgoingCompletedChallengeJsonModel.class;
	
	public CompletedChallengesJsonFormatter () {
		super(iclazzArray, oclazz, "completedchallenges");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompletedChallenge> getResourcesFromJSONFormattedString(String jsonFormattedResourceString) {
		List<IncomingCompletedChallengeJsonModel> jsonModels = (List<IncomingCompletedChallengeJsonModel>) super.getResourcesFromJSONFormattedString(jsonFormattedResourceString);
		return JSONModelToCompletedChallengeConverter.convertToCompletedChallenges(jsonModels);
	}
	@Override
	public String getJSONFormattedStringOfResource(List<? extends Object> objects) {
		@SuppressWarnings("unchecked")
		List <CompletedChallenge> challenges = (List <CompletedChallenge>) objects;
		List<OutgoingCompletedChallengeJsonModel> outgoingModels = JSONModelToCompletedChallengeConverter.convertToOutgoingCompletedChallengeJsonModel(challenges);
		return super.getJSONFormattedStringOfResource(outgoingModels);
	}

}

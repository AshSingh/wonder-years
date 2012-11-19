package com.cs410.getfit.server.challenges.json;

import java.lang.reflect.Type;
import java.util.List;

import com.cs410.getfit.server.json.ResourceFormatter;
import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeHistory;
import com.cs410.getfit.shared.OutgoingChallengeHistoryJsonModel;
/**
 * Contains the functionality to parse between json and challenge history objects
 * @author kiramccoan
 *
 */
public class ChallengeHistoryJsonFormatter extends ResourceFormatter {

	private static Type incomingClazzArray = null; //cannot post to challenge history
	private static Type outgoingClazz = OutgoingChallengeHistoryJsonModel.class;
	private static String encompassingString = "user_newsfeed";

	public ChallengeHistoryJsonFormatter() {
		super(incomingClazzArray, outgoingClazz, encompassingString);
	}
	
	@Override
	public List<Challenge> getResourcesFromJSONFormattedString(String jsonFormattedResourceString) {
		return null; //not supported
	}
	
	@Override
	public String getJSONFormattedStringOfResource(List<? extends Object> objects) {
		@SuppressWarnings("unchecked")
		List <ChallengeHistory> history = (List <ChallengeHistory>) objects;
		List<OutgoingChallengeHistoryJsonModel> outgoingModels = JSONModelToChallengeHistoryConverter.convertToOutgoingChallengeHistoryJsonModel(history);
		return super.getJSONFormattedStringOfResource(outgoingModels);
	}

}

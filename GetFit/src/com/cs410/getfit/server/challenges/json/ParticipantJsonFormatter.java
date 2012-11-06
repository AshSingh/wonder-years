package com.cs410.getfit.server.challenges.json;

import java.lang.reflect.Type;
import java.util.List;

import com.cs410.getfit.server.json.ResourceFormatter;
import com.cs410.getfit.server.models.ChallengeUser;
import com.cs410.getfit.shared.IncomingParticipantJsonModel;
import com.cs410.getfit.shared.OutgoingParticipantJsonModel;

public class ParticipantJsonFormatter extends ResourceFormatter {

	private static Type iclazzArray = IncomingParticipantJsonModel[].class;
	private static Type oclazz = OutgoingParticipantJsonModel.class; 
	
	public ParticipantJsonFormatter() {
		super(iclazzArray, oclazz, "participants");
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ChallengeUser> getResourcesFromJSONFormattedString(String jsonFormattedResourceString) {
		List<IncomingParticipantJsonModel> jsonModels = (List<IncomingParticipantJsonModel>) super.getResourcesFromJSONFormattedString(jsonFormattedResourceString);
		return JSONModelToParticipantConverter.convertToParticipants(jsonModels);
	}

	@Override
	public String getJSONFormattedStringOfResource(List<? extends Object> objects) {
		@SuppressWarnings("unchecked")
		List <ChallengeUser> participants = (List <ChallengeUser>) objects;
		List<OutgoingParticipantJsonModel> outgoingModels = JSONModelToParticipantConverter.convertToOutgoingParticipantJsonModel(participants);
		return super.getJSONFormattedStringOfResource(outgoingModels);
	}
}

package com.cs410.getfit.server.challenges.json;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.server.challenges.json.links.ParticipantSelfLink;
import com.cs410.getfit.server.models.ChallengeUser;
import com.cs410.getfit.server.models.ChallengeUserImpl;
import com.cs410.getfit.server.models.User;
import com.cs410.getfit.server.models.UserImpl;
import com.cs410.getfit.server.users.json.links.UserSelfLink;
import com.cs410.getfit.shared.IncomingParticipantJsonModel;
import com.cs410.getfit.shared.OutgoingParticipantJsonModel;
import com.cs410.getfit.shared.ParticipantInfoJsonModel;
import com.cs410.getfit.shared.ResourceLink;

public class JSONModelToParticipantConverter {

	public static List<ChallengeUser> convertToParticipants(
			List<IncomingParticipantJsonModel> jsonModels) {
		List<ChallengeUser> participants = new ArrayList<ChallengeUser>();
		for (IncomingParticipantJsonModel model : jsonModels) {
			ChallengeUser participant = new ChallengeUserImpl();
			participant.setAdmin(model.isAdmin());
			User user = new UserImpl();
			user.setGuid(model.getUserId());
			participant.setUser(user);
			participants.add(participant);
		}
		return participants;
	}

	public static List<OutgoingParticipantJsonModel> convertToOutgoingParticipantJsonModel(List<ChallengeUser> participants) {
		List<OutgoingParticipantJsonModel> models = new ArrayList<OutgoingParticipantJsonModel>();
		for (ChallengeUser participant : participants) {
			OutgoingParticipantJsonModel model = new OutgoingParticipantJsonModel();
			List<ResourceLink> links = new ArrayList<ResourceLink>();
			ParticipantInfoJsonModel info = new ParticipantInfoJsonModel();
			info.setAdmin(participant.isAdmin());
			info.setDateJoined(participant.getDateJoined());
			info.setUserId(participant.getUser().getGuid());
			
			//self link
			links.add(new ParticipantSelfLink(participant.getChallenge().getGuid(), participant
					.getGuid()));
			//link to user
			links.add(new UserSelfLink(participant.getUser().getGuid()));
			model.setInfo(info);
			model.setLinks(links);
			models.add(model);
		}

		return models;
	}
}

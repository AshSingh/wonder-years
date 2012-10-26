package com.cs410.getfit.server.challenges.json;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.server.json.ResourceLink;
import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeImpl;
import com.cs410.getfit.server.models.ChallengeUser;
import com.cs410.getfit.server.models.ChallengeUserImpl;
import com.cs410.getfit.server.models.User;
import com.cs410.getfit.server.models.UserImpl;

public class JSONModelToChallengeConverter {

	public static List<Challenge> convertToChallenges(
			List<IncomingChallengeJsonModel> jsonModels) {
		List<Challenge> challenges = new ArrayList<Challenge>();
		for (IncomingChallengeJsonModel model : jsonModels) {
			String title = model.getTitle();
			boolean isPrivate = model.isIsprivate();
			String location = model.getLocation();
			Long startDate = model.getStartdate();
			Long endDate = model.getEnddate();
			String admin = model.getAdminId();
			List<ChallengeUser> participants = new ArrayList<ChallengeUser>();
			if (admin != null) {
				ChallengeUser challengeUser = new ChallengeUserImpl();
				User user = new UserImpl();
				user.setUsername(admin);
				challengeUser.setUser(user);
				participants.add(challengeUser);
			}
			Challenge challenge = new ChallengeImpl(title, isPrivate, location,
					startDate, endDate, participants);
			challenges.add(challenge);
		}
		return challenges;
	}

	public static List<OutgoingChallengeJsonModel> convertToOutgoingChallengeJsonModel(
			List<Challenge> challenges) {
		List<OutgoingChallengeJsonModel> outgoingModels = new ArrayList<OutgoingChallengeJsonModel>();
		for (Challenge challenge : challenges) {
			ChallengeInfoJsonModel info = new ChallengeInfoJsonModel();
			info.setTitle(challenge.getTitle());
			info.setStartdate(challenge.getStartDate());
			info.setEnddate(challenge.getEndDate());
			info.setIsprivate(challenge.isPrivate());
			info.setLocation(challenge.getLocation());

			// add links for each resource here.
			List<ResourceLink> links = new ArrayList<ResourceLink>();
			Long challengeGuid = challenge.getGuid();
			links.add(new ChallengeLink(challengeGuid));
			links.add(new ChallengeParticipantsLink(challengeGuid));
			/**for (ChallengeUser participant : challenge.getParticipants()) {
				links.add(new ChallengeParticipantLink(challengeGuid,
						participant.getGuid()));
			}**/
			OutgoingChallengeJsonModel model = new OutgoingChallengeJsonModel();
			model.setInfo(info);
			model.setLinks(links);
			outgoingModels.add(model);
		}
		return outgoingModels;
	}
}

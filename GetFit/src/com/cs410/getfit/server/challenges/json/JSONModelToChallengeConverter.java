package com.cs410.getfit.server.challenges.json;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.server.challenges.json.links.ChallengeSelfLink;
import com.cs410.getfit.server.challenges.json.links.CompletedChallengesLink;
import com.cs410.getfit.server.challenges.json.links.ParticipantsLink;
import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeImpl;
import com.cs410.getfit.server.models.ChallengeUser;
import com.cs410.getfit.server.models.ChallengeUserImpl;
import com.cs410.getfit.server.models.User;
import com.cs410.getfit.server.models.UserImpl;
import com.cs410.getfit.shared.ChallengeInfoJsonModel;
import com.cs410.getfit.shared.IncomingChallengeJsonModel;
import com.cs410.getfit.shared.OutgoingChallengeJsonModel;
import com.cs410.getfit.shared.ResourceLink;

public class JSONModelToChallengeConverter {

	/**
	 * convert from a json model of challenges to a list of server challenges
	 * @param jsonModels list of json challenges
	 * @return list of challenges
	 */
	public static List<Challenge> convertToChallenges(
			List<IncomingChallengeJsonModel> jsonModels) {
		List<Challenge> challenges = new ArrayList<Challenge>();
		for (IncomingChallengeJsonModel model : jsonModels) {
			String title = model.getTitle();
			Boolean isPrivate = model.getIsprivate();
			String location = model.getLocation();
			String description = model.getDescription();
			Long admin = model.getAdminId();
			List<ChallengeUser> participants = new ArrayList<ChallengeUser>();
			if (admin != null) {
				ChallengeUser challengeUser = new ChallengeUserImpl();
				User user = new UserImpl();
				user.setGuid(admin);
				challengeUser.setUser(user);
				participants.add(challengeUser);
			}
			Challenge challenge = new ChallengeImpl(title, isPrivate, location,
					description, participants);
			challenges.add(challenge);
		}
		return challenges;
	}
	/**
	 * convert a list of challenges to outgoing json model containing links to sub  and related resources
	 * @param challenges
	 * @return list of json challenges
	 */
	public static List<OutgoingChallengeJsonModel> convertToOutgoingChallengeJsonModel(
			List<Challenge> challenges) {
		List<OutgoingChallengeJsonModel> outgoingModels = new ArrayList<OutgoingChallengeJsonModel>();
		for (Challenge challenge : challenges) {
			ChallengeInfoJsonModel info = new ChallengeInfoJsonModel();
			info.setTitle(challenge.getTitle());
			info.setDescription(challenge.getDescription());
			info.setIsprivate(challenge.isPrivate());
			info.setLocation(challenge.getLocation());

			// add links for each resource here.
			List<ResourceLink> links = new ArrayList<ResourceLink>();
			Long challengeGuid = challenge.getGuid();
			links.add(new ChallengeSelfLink(challengeGuid));
			links.add(new ParticipantsLink(challengeGuid));
			links.add(new CompletedChallengesLink(challengeGuid));

			OutgoingChallengeJsonModel model = new OutgoingChallengeJsonModel();
			model.setInfo(info);
			model.setLinks(links);
			outgoingModels.add(model);
		}
		return outgoingModels;
	}
}

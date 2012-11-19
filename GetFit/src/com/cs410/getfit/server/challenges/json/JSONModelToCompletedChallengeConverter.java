package com.cs410.getfit.server.challenges.json;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.server.challenges.json.links.CompletedChallengeSelfLink;
import com.cs410.getfit.server.models.CompletedChallenge;
import com.cs410.getfit.server.models.CompletedChallengeImpl;
import com.cs410.getfit.server.models.User;
import com.cs410.getfit.server.models.UserImpl;
import com.cs410.getfit.server.users.json.links.UserSelfLink;
import com.cs410.getfit.shared.CompletedChallengeInfoJsonModel;
import com.cs410.getfit.shared.IncomingCompletedChallengeJsonModel;
import com.cs410.getfit.shared.OutgoingCompletedChallengeJsonModel;
import com.cs410.getfit.shared.ResourceLink;

public class JSONModelToCompletedChallengeConverter {
	/**
	 * convert from a json model of completed challenges to a list of server completed challenges
	 * @param jsonModels list of json completed challenges
	 * @return list of completed challenges
	 */
	public static List<CompletedChallenge> convertToCompletedChallenges(
			List<IncomingCompletedChallengeJsonModel> jsonModels) {
		List<CompletedChallenge> c_challenges = new ArrayList<CompletedChallenge>();
		for (IncomingCompletedChallengeJsonModel model : jsonModels) {
			CompletedChallenge c_challenge = new CompletedChallengeImpl();
			User user = new UserImpl();
			user.setGuid(model.getUserId());
			c_challenge.setUser(user);
			c_challenges.add(c_challenge);
		}
		return c_challenges;
	}
	/**
	 * convert a list of completed challenges to their outgoing json form
	 * @param c_challenges challenges to convert to json form
	 * @return a list of outgoing json models
	 */
	public static List<OutgoingCompletedChallengeJsonModel> convertToOutgoingCompletedChallengeJsonModel(
			List<CompletedChallenge> c_challenges) {
		List<OutgoingCompletedChallengeJsonModel> models = new ArrayList<OutgoingCompletedChallengeJsonModel>();
		for (CompletedChallenge c_challenge : c_challenges) {
			OutgoingCompletedChallengeJsonModel model = new OutgoingCompletedChallengeJsonModel();
			List<ResourceLink> links = new ArrayList<ResourceLink>();
			CompletedChallengeInfoJsonModel info = new CompletedChallengeInfoJsonModel();
			info.setDateCompleted(c_challenge.getDateCompleted());
			info.setUserId(c_challenge.getUser().getGuid());
			
			//self link
			links.add(new CompletedChallengeSelfLink(c_challenge.getChallenge().getGuid(), c_challenge
					.getGuid()));
			//link to user
			links.add(new UserSelfLink(c_challenge.getUser().getGuid()));
			
			model.setInfo(info);
			model.setLinks(links);
			models.add(model);
		}

		return models;
	}

}

package com.cs410.getfit.server.challenges.json.links;

import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.ResourceLink;
/**
 * represents the uri to the list of completed challenges under a specific challenge
 * @author kiramccoan
 *
 */
public class CompletedChallengesLink extends ResourceLink {
	public CompletedChallengesLink(Long challengeId) {
		super("/challenges/"+challengeId, "/completedchallenges", LinkTypes.COMPLETEDCHALLENGES.toString());
	}

}

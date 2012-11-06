package com.cs410.getfit.server.challenges.json.links;

import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.ResourceLink;

public class CompletedChallengesLink extends ResourceLink {
	public CompletedChallengesLink(Long challengeId) {
		super("/challenges/"+challengeId, "/completedchallenges", LinkTypes.COMPLETEDCHALLENGES.toString());
	}

}

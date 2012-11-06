package com.cs410.getfit.server.challenges.json.links;

import com.cs410.getfit.server.json.ResourceLink;

public class CompletedChallengesLink extends ResourceLink {
	public CompletedChallengesLink(Long challengeId) {
		super("/challenges/"+challengeId, "/completedchallenges", "completedchallenges");
	}

}

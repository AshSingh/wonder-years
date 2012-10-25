package com.cs410.getfit.server.challenges.json;

import com.cs410.getfit.server.json.ResourceLink;

public class ChallengeParticipantsLink extends ResourceLink{

	public ChallengeParticipantsLink(Long challengeId) {
		super("/challenges/"+challengeId, "/participants", "participants");
	}

}

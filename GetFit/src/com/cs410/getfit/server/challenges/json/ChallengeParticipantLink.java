package com.cs410.getfit.server.challenges.json;

import com.cs410.getfit.server.json.ResourceLink;

public class ChallengeParticipantLink extends ResourceLink{

	public ChallengeParticipantLink(Long challengeId, Long participantId) {
		super("/challenges/"+challengeId, "/participants/"+participantId, "participant");
	}

}

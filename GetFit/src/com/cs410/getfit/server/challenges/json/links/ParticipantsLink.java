package com.cs410.getfit.server.challenges.json.links;

import com.cs410.getfit.server.json.ResourceLink;

public class ParticipantsLink extends ResourceLink{

	public ParticipantsLink(Long challengeId) {
		super("/challenges/"+challengeId, "/participants", "participants");
	}

}

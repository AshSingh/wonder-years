package com.cs410.getfit.server.challenges.json.links;

import com.cs410.getfit.server.json.ResourceLink;

public class ParticipantSelfLink extends ResourceLink {
	public ParticipantSelfLink(Long challengeGuid, long participantId){
		super("self", "/challenges/"+challengeGuid+"/participants/"+participantId,"participant");
	}
}

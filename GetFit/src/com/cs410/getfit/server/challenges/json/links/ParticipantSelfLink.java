package com.cs410.getfit.server.challenges.json.links;

import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.ResourceLink;
/**
 * represents the uri to a specific participant under a specific challenge
 * @author kiramccoan
 *
 */
public class ParticipantSelfLink extends ResourceLink {
	public ParticipantSelfLink(Long challengeGuid, long participantId){
		super("self", "/challenges/"+challengeGuid+"/participants/"+participantId,LinkTypes.PARTICIPANT.toString());
	}
}

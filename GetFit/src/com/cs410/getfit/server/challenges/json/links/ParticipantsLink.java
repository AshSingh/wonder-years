package com.cs410.getfit.server.challenges.json.links;

import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.ResourceLink;
/**
 * represents the uri to a list of participants for a specific challenge
 * @author kiramccoan
 *
 */
public class ParticipantsLink extends ResourceLink{

	public ParticipantsLink(Long challengeId) {
		super("/challenges/"+challengeId, "/participants", LinkTypes.PARTICIPANTS.toString());
	}

}

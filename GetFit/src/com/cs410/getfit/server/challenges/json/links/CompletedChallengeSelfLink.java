package com.cs410.getfit.server.challenges.json.links;

import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.ResourceLink;

/**
 * Represents the uri to a specific completed challenge
 * @author kiramccoan
 *
 */
public class CompletedChallengeSelfLink extends ResourceLink {
	public CompletedChallengeSelfLink(long challengeGuid, long completdChallengeId){
		super("self", "/challenges/"+challengeGuid+"/completedchallenges/"+completdChallengeId,LinkTypes.COMPLETEDCHALLENGE.toString());
	}
}

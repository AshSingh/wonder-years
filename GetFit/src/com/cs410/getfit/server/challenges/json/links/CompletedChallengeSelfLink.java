package com.cs410.getfit.server.challenges.json.links;

import com.cs410.getfit.server.json.ResourceLink;

public class CompletedChallengeSelfLink extends ResourceLink {
	public CompletedChallengeSelfLink(long challengeGuid, long completdChallengeId){
		super("self", "/challenges/"+challengeGuid+"/completedchallenges/"+completdChallengeId,"completedchallenge");
	}
}

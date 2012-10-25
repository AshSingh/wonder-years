package com.cs410.getfit.server.challenges.json;

import com.cs410.getfit.server.json.ResourceLink;

public class ChallengeLink extends ResourceLink{
	
	public ChallengeLink(Long challengeGuid){
		super("self", "/challenges/"+challengeGuid,"challenge");
	}	
}

package com.cs410.getfit.server.challenges.json.links;

import com.cs410.getfit.server.json.ResourceLink;

public class ChallengeSelfLink extends ResourceLink{
	
	public ChallengeSelfLink(Long challengeGuid){
		super("self", "/challenges/"+challengeGuid,"challenge");
	}	
}

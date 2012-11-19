package com.cs410.getfit.server.challenges.json.links;

import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.ResourceLink;

/**
 * Represents the uri to a specific challenge
 * @author kiramccoan
 *
 */
public class ChallengeSelfLink extends ResourceLink{
	
	public ChallengeSelfLink(Long challengeGuid){
		super("self", "/challenges/"+challengeGuid,LinkTypes.CHALLENGE.toString());
	}	
}

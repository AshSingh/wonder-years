package com.cs410.getfit.server.challenges.json.links;

import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.ResourceLink;

public class ChallengeHistorySelfLink extends ResourceLink{
	
	public ChallengeHistorySelfLink(long userId, long newsfeedId){
		super("self", "/users/"+userId+"/newsfeed/"+newsfeedId,LinkTypes.NEWSFEEDITEM.toString());
	}	
}

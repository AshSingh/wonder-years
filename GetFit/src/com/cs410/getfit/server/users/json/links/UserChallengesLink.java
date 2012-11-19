package com.cs410.getfit.server.users.json.links;

import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.ResourceLink;
/**
 * Represents a uri for a users challenges
 * @author kiramccoan
 *
 */
public class UserChallengesLink extends ResourceLink {
	public UserChallengesLink(long userId){
		super("/users/"+userId,"/challenges",LinkTypes.USERCHALLENGES.toString());
	}
}

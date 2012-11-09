package com.cs410.getfit.server.users;

import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.ResourceLink;

public class UserChallengesLink extends ResourceLink {
	public UserChallengesLink(long userId){
		super("/users/"+userId,"/challenges",LinkTypes.USERCHALLENGES.toString());
	}
}

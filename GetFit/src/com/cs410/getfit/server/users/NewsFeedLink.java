package com.cs410.getfit.server.users;

import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.ResourceLink;

public class NewsFeedLink extends ResourceLink {
	public NewsFeedLink(long userId){
		super("/users/"+userId,"/newsfeed",LinkTypes.NEWSFEED.toString());
	}
}

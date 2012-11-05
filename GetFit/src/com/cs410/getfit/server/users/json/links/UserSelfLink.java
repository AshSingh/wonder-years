package com.cs410.getfit.server.users.json.links;

import com.cs410.getfit.server.json.LinkTypes;
import com.cs410.getfit.server.json.ResourceLink;

public class UserSelfLink extends ResourceLink {
	public UserSelfLink(long userId) {
		super("self", "/users/" + userId, LinkTypes.USER.toString());
	}
}

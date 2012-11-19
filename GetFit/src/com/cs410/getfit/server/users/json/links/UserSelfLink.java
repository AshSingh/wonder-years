package com.cs410.getfit.server.users.json.links;

import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.ResourceLink;
/**
 * represents a uri to a user
 * @author kiramccoan
 *
 */
public class UserSelfLink extends ResourceLink {
	public UserSelfLink(long userId) {
		super("self", "/users/" + userId, LinkTypes.USER.toString());
	}
}

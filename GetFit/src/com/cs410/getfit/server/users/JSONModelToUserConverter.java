package com.cs410.getfit.server.users;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.server.models.User;
import com.cs410.getfit.server.users.json.links.UserSelfLink;
import com.cs410.getfit.shared.OutgoingUserJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.cs410.getfit.shared.UserInfoJsonModel;

public class JSONModelToUserConverter {

	public static List<OutgoingUserJsonModel> convertToOutgoingUserJsonModel(
			List<User> users) {
		List<OutgoingUserJsonModel> outgoingModels = new ArrayList<OutgoingUserJsonModel>();
		for (User user : users) {
			UserInfoJsonModel info = new UserInfoJsonModel();
			info.setFirstname(user.getFirstName());
			info.setLastname(user.getLastName());
			info.setIsPrivate(user.getIsPrivate());

			// add links for each resource here.
			List<ResourceLink> links = new ArrayList<ResourceLink>();
			Long userId = user.getGuid();
			links.add(new UserSelfLink(userId));
			links.add(new NewsFeedLink(userId));
			links.add(new UserChallengesLink(userId));

			OutgoingUserJsonModel model = new OutgoingUserJsonModel();
			model.setInfo(info);
			model.setLinks(links);
			outgoingModels.add(model);
		}
		return outgoingModels;
	}

}

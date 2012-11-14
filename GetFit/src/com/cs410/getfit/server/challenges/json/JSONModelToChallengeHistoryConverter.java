package com.cs410.getfit.server.challenges.json;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.server.challenges.json.links.ChallengeSelfLink;
import com.cs410.getfit.server.models.ChallengeHistory;
import com.cs410.getfit.server.users.json.links.UserSelfLink;
import com.cs410.getfit.shared.ChallengeHistoryInfoJsonModel;
import com.cs410.getfit.shared.OutgoingChallengeHistoryJsonModel;
import com.cs410.getfit.shared.ResourceLink;

public class JSONModelToChallengeHistoryConverter {

	public static List<OutgoingChallengeHistoryJsonModel> convertToOutgoingChallengeHistoryJsonModel(
			List<ChallengeHistory> history) {
		List<OutgoingChallengeHistoryJsonModel> outgoing = new ArrayList<OutgoingChallengeHistoryJsonModel>();
		for(ChallengeHistory item: history) {
			OutgoingChallengeHistoryJsonModel model = new OutgoingChallengeHistoryJsonModel();
			ChallengeHistoryInfoJsonModel info = new ChallengeHistoryInfoJsonModel();
			info.setDatemodified(item.getDateModified());
			info.setNewsfeedItemType(item.getHistoryDescription());
			
			List <ResourceLink> links = new ArrayList<ResourceLink>();
			links.add(new UserSelfLink(item.getUser().getGuid()));
			links.add(new ChallengeSelfLink(item.getChallenge().getGuid()));
			model.setInfo(info);
			model.setLinks(links);
			outgoing.add(model);
		}
		return outgoing;
	}

}

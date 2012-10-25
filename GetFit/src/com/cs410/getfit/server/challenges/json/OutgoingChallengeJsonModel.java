package com.cs410.getfit.server.challenges.json;

import java.util.List;

import com.cs410.getfit.server.json.ResourceLink;

public class OutgoingChallengeJsonModel {
	private ChallengeInfoJsonModel info;
	private List <ResourceLink> links;
	public void setInfo(ChallengeInfoJsonModel info) {
		this.info = info;
	}
	public void setLinks(List<ResourceLink> links) {
		this.links = links;
	}
	
	
}

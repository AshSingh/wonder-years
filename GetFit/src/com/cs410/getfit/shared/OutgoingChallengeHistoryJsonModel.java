package com.cs410.getfit.shared;

import java.util.List;

public class OutgoingChallengeHistoryJsonModel {
	private ChallengeHistoryInfoJsonModel info;
	private List <ResourceLink> links;
	public ChallengeHistoryInfoJsonModel getInfo() {
		return info;
	}
	public void setInfo(ChallengeHistoryInfoJsonModel info) {
		this.info = info;
	}
	public List <ResourceLink> getLinks() {
		return links;
	}
	public void setLinks(List <ResourceLink> links) {
		this.links = links;
	}
}

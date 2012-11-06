package com.cs410.getfit.shared;

import java.util.List;


public class OutgoingCompletedChallengeJsonModel {
	private CompletedChallengeInfoJsonModel info;
	private List <ResourceLink> links;
	public void setInfo(CompletedChallengeInfoJsonModel info) {
		this.info = info;
	}
	public void setLinks(List<ResourceLink> links) {
		this.links = links;
	}
	public CompletedChallengeInfoJsonModel getInfo() {
		return info;
	}
	public List<ResourceLink> getLinks() {
		return links;
	}
}

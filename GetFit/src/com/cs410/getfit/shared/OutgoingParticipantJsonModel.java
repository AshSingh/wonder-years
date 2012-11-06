package com.cs410.getfit.shared;

import java.util.List;


public class OutgoingParticipantJsonModel {
	private ParticipantInfoJsonModel info;
	private List <ResourceLink> links;
	public void setInfo(ParticipantInfoJsonModel info) {
		this.info = info;
	}
	public void setLinks(List<ResourceLink> links) {
		this.links = links;
	}
	public ParticipantInfoJsonModel getInfo() {
		return info;
	}
	public List<ResourceLink> getLinks() {
		return links;
	}
}

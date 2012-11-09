package com.cs410.getfit.shared;

import java.util.List;

public class OutgoingUserJsonModel {
	UserInfoJsonModel info;
	List<ResourceLink> links;
	public UserInfoJsonModel getInfo() {
		return info;
	}
	public void setInfo(UserInfoJsonModel info) {
		this.info = info;
	}
	public List<ResourceLink> getLinks() {
		return links;
	}
	public void setLinks(List<ResourceLink> links) {
		this.links = links;
	}
}

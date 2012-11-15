package com.cs410.getfit.shared;

import java.util.List;


public class OutgoingChallengeJsonModel {
	private ChallengeInfoJsonModel info;
	private List <ResourceLink> links;
	private double distance;
	public void setInfo(ChallengeInfoJsonModel info) {
		this.info = info;
	}
	public void setLinks(List<ResourceLink> links) {
		this.links = links;
	}
	public ChallengeInfoJsonModel getInfo(){
		return info;
	}
	public List<ResourceLink> getLinks(){
		return links;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public double getDistance() {
		return this.distance;
	}
	
}

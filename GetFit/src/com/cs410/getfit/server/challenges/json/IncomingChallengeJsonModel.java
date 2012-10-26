package com.cs410.getfit.server.challenges.json;


public class IncomingChallengeJsonModel {
	private ChallengeInfoJsonModel info;
	private Long admin;
	
	public String getTitle() {
		return info.getTitle();
	}
	public long getStartdate() {
		return info.getStartdate();
	}
	public long getEnddate() {
		return info.getEnddate();
	}
	public String getLocation() {
		return info.getLocation();
	}
	public Boolean setIsprivate() {
		return info.setIsprivate();
	}
	public Long getAdminId() {
		return admin;
	}
}

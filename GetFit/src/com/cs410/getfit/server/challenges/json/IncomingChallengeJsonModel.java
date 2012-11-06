package com.cs410.getfit.server.challenges.json;


public class IncomingChallengeJsonModel {
	private ChallengeInfoJsonModel info;
	private Long admin;
	
	public String getTitle() {
		return info.getTitle();
	}
	public String getDescription() {
		return info.getDescription();
	}
	public String getLocation() {
		return info.getLocation();
	}
	public Boolean getIsprivate() {
		return info.getIsprivate();
	}
	public Long getAdminId() {
		return admin;
	}
	public void setChallengeInfoJsonModel(ChallengeInfoJsonModel info) {
		this.info = info;
	}
	public void setAdminId(Long admin) {
		this.admin = admin;
	}
}

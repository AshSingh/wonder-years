package com.cs410.getfit.shared;


public class ParticipantInfoJsonModel {
	private long userId;
	private Boolean isAdmin;
	private long dateJoined;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getDateJoined() {
		return dateJoined;
	}
	public void setDateJoined(long dateJoined) {
		this.dateJoined = dateJoined;
	}
	public Boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}

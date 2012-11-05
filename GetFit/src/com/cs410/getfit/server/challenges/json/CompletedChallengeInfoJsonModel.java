package com.cs410.getfit.server.challenges.json;

public class CompletedChallengeInfoJsonModel {
	private long userId;
	private long dateCompleted;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getDateCompleted() {
		return dateCompleted;
	}
	public void setDateCompleted(long dateCompleted) {
		this.dateCompleted = dateCompleted;
	}
	
}

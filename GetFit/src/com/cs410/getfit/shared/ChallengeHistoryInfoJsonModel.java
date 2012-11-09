package com.cs410.getfit.shared;

public class ChallengeHistoryInfoJsonModel {
	private long datemodified;
	private String newsfeedItemType;
	
	public String getNewsfeedItemType() {
		return newsfeedItemType;
	}
	public void setNewsfeedItemType(String newsfeedItemType) {
		this.newsfeedItemType = newsfeedItemType;
	}
	public long getDatemodified() {
		return datemodified;
	}
	public void setDatemodified(long datemodified) {
		this.datemodified = datemodified;
	}
	
}

package com.cs410.getfit.shared;


public class IncomingCompletedChallengeJsonModel {
	private CompletedChallengeInfoJsonModel info;
	
	public long getUserId(){
		return info.getUserId();
	}

	public void setUserId(long userId){
		this.info.setUserId(userId);
	}
	
	public void setCompletedChallengeInfoJsonModel(CompletedChallengeInfoJsonModel info) {
		this.info = info;
	}
}

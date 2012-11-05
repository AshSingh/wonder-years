package com.cs410.getfit.server.challenges.json;

public class IncomingCompletedChallengeJsonModel {
	private CompletedChallengeInfoJsonModel info;
	
	public long getUserId(){
		return info.getUserId();
	}
}

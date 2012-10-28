package com.cs410.getfit.server.challenges.json;

public class IncomingParticipantJsonModel {
	private ParticipantInfoJsonModel info;
	
	public long getUserId(){
		return info.getUserId();
	}
	public Boolean isAdmin() {
		return info.isAdmin();
	}
}

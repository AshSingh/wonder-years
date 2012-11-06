package com.cs410.getfit.shared;


public class IncomingParticipantJsonModel {
	private ParticipantInfoJsonModel info;
	
	public long getUserId(){
		return info.getUserId();
	}
	public Boolean isAdmin() {
		return info.isAdmin();
	}
	public void setParticipantInfoJsonModel(ParticipantInfoJsonModel info) {
		this.info = info;
	}
}

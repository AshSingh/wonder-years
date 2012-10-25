package com.cs410.getfit.server.challenges.json;

import java.util.ArrayList;
import java.util.List;

public class IncomingChallengeJsonModel {
	private ChallengeInfoJsonModel info;
	private List <ChallengeUserJsonModel> participants = new ArrayList<ChallengeUserJsonModel>();
	
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
	public boolean isIsprivate() {
		return info.isIsprivate();
	}
	public List<ChallengeUserJsonModel> getParticipants() {
		return participants;
	}
}

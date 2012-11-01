package com.cs410.getfit.server.models;

import java.util.List;


public interface Challenge {
	
	public long getGuid();
	public void setGuid(Long guid);
	public String getTitle();
	public void setTitle(String title);
	public Boolean isPrivate();
	public void setIsPrivate(Boolean isPrivate);
	public String getLocation();
	public void setLocation(String location);
	public String getDescription();
	public void setDescription(String description);
	public List <ChallengeUser> getParticipants();
	public void setParticipants(List <ChallengeUser> participants);
}

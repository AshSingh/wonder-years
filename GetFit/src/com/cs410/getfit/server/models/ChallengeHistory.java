package com.cs410.getfit.server.models;
/**
 * Represents a user based event that occurs to a challenge
 * @author kiramccoan
 *
 */
public interface ChallengeHistory{

	public long getGuid();
	public void setGuid(long guid);
	
	public User getUser();
	public void setUser(User user);
	
	public Challenge getChallenge();
	public void setChallenge(Challenge challenge);
	
	public long getDateModified();
	public void setDateModified(long dateModified);
	
	public String getHistoryDescription();
	public void setHistoryDescription(String histDesc);
}

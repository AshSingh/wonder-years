package com.cs410.getfit.server.models;
/**
 * Represents a completed challenge
 * @author kiramccoan
 *
 */
public interface CompletedChallenge {
	
	public long getGuid();
	public void setGuid(Long guid);
	
	public User getUser();
	public void setUser(User user);

	public Challenge getChallenge();
	public void setChallenge(Challenge challenge);

	public long getDateCompleted();
	public void setDateCompleted(long dateCompleted);
}

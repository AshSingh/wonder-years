package com.cs410.getfit.server.models;

public interface CompletedChallenge {
	
	public long getGuid();
	public void setGuid(Long guid);
	
	public abstract User getUser();
	public abstract void setUser(User user);

	public abstract Challenge getChallenge();
	public abstract void setChallenge(Challenge challenge);

	public abstract long getDateCompleted();
	public abstract void setDateCompleted(long dateCompleted);
}

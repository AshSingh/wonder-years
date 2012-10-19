package com.cs410.getfit.shared;


public interface ChallengeUsers {

	public abstract UserImpl getUser();

	public abstract void setUser(UserImpl user);

	public abstract ChallengeImpl getChallenge();

	public abstract void setChallenge(ChallengeImpl challenge);

	public abstract boolean isAdmin();

	public abstract void setAdmin(boolean isAdmin);

	public abstract boolean isSubscribed();

	public abstract void setSubscribed(boolean isSubscribed);

	public abstract long getCreationDate();

	public abstract void setCreationDate(long creationDate);

	public abstract long getGuid();

}
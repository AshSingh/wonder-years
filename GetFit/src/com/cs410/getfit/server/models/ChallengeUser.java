package com.cs410.getfit.server.models;


public interface ChallengeUser {

	public abstract User getUser();

	public abstract void setUser(User user);

	public abstract Challenge getChallenge();

	public abstract void setChallenge(Challenge challenge);

	public abstract Boolean isAdmin();

	public abstract void setAdmin(Boolean isAdmin);

	public abstract Boolean isSubscribed();

	public abstract void setSubscribed(Boolean isSubscribed);

	public abstract long getDateJoined();

	public abstract void setDateJoined(long dateJoined);

	public abstract long getGuid();

	public abstract void setGuid(Long guid);

}
package com.cs410.getfit.server.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "challengeUsers")
public class ChallengeUserImpl implements ChallengeUser {

	@DatabaseField(generatedId = true)
	private long guid;
	@DatabaseField(canBeNull = false, foreign = true)
	private UserImpl user;
	@DatabaseField(canBeNull = false, foreign = true)
	private ChallengeImpl challenge;
	@DatabaseField(canBeNull = false, defaultValue="false")
	private boolean isAdmin;
	@DatabaseField(canBeNull = false,defaultValue = "true")
	private boolean isSubscribed;
	@DatabaseField
	private long dateJoined;
	
	public ChallengeUserImpl() {
		//for bean def
	}
	
	public ChallengeUserImpl(User user, Challenge challenge, boolean isAdmin, boolean isSubscribed, long creationDate) {
		setUser(user);
		setChallenge(challenge);
		setAdmin(isAdmin);
		setSubscribed(isSubscribed);
		setDateJoined(creationDate);
	}
	@Override
	public UserImpl getUser() {
		return user;
	}
	@Override
	public void setUser(User user) {
		if(user instanceof UserImpl)
			this.user = (UserImpl) user;
	}
	@Override
	public ChallengeImpl getChallenge() {
		return challenge;
	}
	@Override
	public void setChallenge(Challenge challenge) {
		if(challenge instanceof ChallengeImpl)
			this.challenge = (ChallengeImpl) challenge;
	}
	@Override
	public boolean isAdmin() {
		return isAdmin;
	}
	@Override
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	@Override
	public boolean isSubscribed() {
		return isSubscribed;
	}
	@Override
	public void setSubscribed(boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}
	@Override
	public long getDateJoined() {
		return dateJoined;
	}
	@Override
	public void setDateJoined(long creationDate) {
		this.dateJoined = creationDate;
	}
	@Override
	public long getGuid() {
		return guid;
	}

	@Override //for testing purposes only
	public void setGuid(Long guid) {
		this.guid= guid;
	}
}

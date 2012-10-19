package com.cs410.getfit.shared;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "challengeUsers")
public class ChallengeUsersImpl implements ChallengeUsers {

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
	private long creationDate;
	
	public ChallengeUsersImpl() {
		//for bean def
	}
	
	public ChallengeUsersImpl(UserImpl user, ChallengeImpl challenge, boolean isAdmin, boolean isSubscribed, long creationDate) {
		setUser(user);
		setChallenge(challenge);
		setAdmin(isAdmin);
		setSubscribed(isSubscribed);
		setCreationDate(creationDate);
	}
	@Override
	public UserImpl getUser() {
		return user;
	}
	@Override
	public void setUser(UserImpl user) {
		this.user = user;
	}
	@Override
	public ChallengeImpl getChallenge() {
		return challenge;
	}
	@Override
	public void setChallenge(ChallengeImpl challenge) {
		this.challenge = challenge;
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
	public long getCreationDate() {
		return creationDate;
	}
	@Override
	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}
	@Override
	public long getGuid() {
		return guid;
	}
}

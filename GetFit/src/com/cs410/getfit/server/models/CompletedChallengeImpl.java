package com.cs410.getfit.server.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "completedchallenges")
public class CompletedChallengeImpl implements CompletedChallenge {
	@DatabaseField(generatedId = true)
	private long guid;
	
	@DatabaseField(columnDefinition = " bigint(20), FOREIGN KEY (user_id) "+
				"REFERENCES Users(guid)",canBeNull = false, foreign = true)
	private UserImpl user;
	
	@DatabaseField(columnDefinition = " bigint(20), FOREIGN KEY (challenge_id) "+
			"REFERENCES Challenges(guid)",canBeNull = false, foreign = true)
	private ChallengeImpl challenge;

	@DatabaseField(canBeNull = false)
	private long dateCompleted;

	public CompletedChallengeImpl() {
		// for bean def
	}

	public CompletedChallengeImpl(UserImpl user, ChallengeImpl challenge, long dateCompleted) {
		setUser(user);
		setChallenge(challenge);
		setDateCompleted(dateCompleted);
	}
	
	@Override
	public long getGuid() {
		return guid;
	}

	@Override
	public void setGuid(Long guid) {
		this.guid = guid;
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public void setUser(User user) {
		if(user instanceof UserImpl)
			this.user = (UserImpl) user;
	}

	@Override
	public Challenge getChallenge() {
		return challenge;
	}

	@Override
	public void setChallenge(Challenge challenge) {
		if(challenge instanceof ChallengeImpl)
			this.challenge = (ChallengeImpl) challenge;
	}

	@Override
	public long getDateCompleted() {
		return dateCompleted;
	}

	@Override
	public void setDateCompleted(long dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

}

package com.cs410.getfit.server.models;

import java.util.Calendar;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "challengehistory")
public class ChallengeHistoryImpl implements ChallengeHistory {

	@DatabaseField(generatedId = true)
	private long guid;
	
	@DatabaseField(columnDefinition = " bigint(20), FOREIGN KEY (challenge_id) "
			+ "REFERENCES Challenges(guid)", canBeNull = false, foreign = true)
	private ChallengeImpl challenge;
	
	@DatabaseField(foreign = true, canBeNull = true)
	private UserImpl user;
	
	@DatabaseField
	private long datemodified;
	
	@DatabaseField
	private String historyDescription;

	public ChallengeHistoryImpl(User user, Challenge challenge, String desc) {
		setUser(user);
		setChallenge(challenge);
		this.historyDescription = desc;
		this.datemodified = Calendar.getInstance().getTimeInMillis();
	}
	public ChallengeHistoryImpl() {
		//bean impl
	}
	@Override
	public long getGuid() {
		return guid;
	}

	@Override
	public void setGuid(long guid) {
		this.guid = guid;
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
	public String getHistoryDescription() {
		return historyDescription;
	}

	@Override
	public void setHistoryDescription(String histDesc) {
		this.historyDescription = histDesc;
	}

	@Override
	public long getDateModified() {
		return datemodified;
	}

	@Override
	public void setDateModified(long dateModified) {
		this.datemodified = dateModified;
		
	}

}

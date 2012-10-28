package com.cs410.getfit.server.challenges.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeImpl;
import com.cs410.getfit.server.models.ChallengeUser;
import com.cs410.getfit.server.models.User;
import com.ibm.icu.util.Calendar;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

public class ParticipantsServices implements ParticipantResourceServices {

	private long challengeId;
	private List<ChallengeUser> participants;
	private Dao<User, Long> userDao;
	private Dao<ChallengeUser, Long> challengeUserDao;
	private TransactionManager manager;

	public Dao<ChallengeUser, Long> getChallengeUserDao() {
		return challengeUserDao;
	}

	public void setChallengeUserDao(Dao<ChallengeUser, Long> challengeUserDao) {
		this.challengeUserDao = challengeUserDao;
	}

	public TransactionManager getTransactionManager() {
		return manager;
	}

	public void setTransactionManager(TransactionManager manager) {
		this.manager = manager;
	}

	public void setChallengeId(long challengeId) {
		this.challengeId = challengeId;
	}

	public Dao <User, Long> getUserDao() {
		return userDao;
	}

	public void setUserDao(Dao <User, Long> userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<ChallengeUser> create() throws SQLException {
		final Challenge challenge = new ChallengeImpl();
		challenge.setGuid(challengeId);
		List<ChallengeUser> challengeUsersCreated = new ArrayList<ChallengeUser>();
		challengeUsersCreated = manager
				.callInTransaction(new Callable<List<ChallengeUser>>() {
					public List<ChallengeUser> call() throws Exception {
						List<ChallengeUser> created = new ArrayList<ChallengeUser>();
						for (ChallengeUser participant : participants) {
							if (participant.getUser() == null) { 
								return null;// dont create if no user is defined
							}
						}
						for (ChallengeUser participant: participants) {
								participant.setChallenge(challenge);
								participant.setDateJoined(Calendar
										.getInstance().getTimeInMillis());
								ChallengeUser participant_created = challengeUserDao
										.createIfNotExists(participant);
								created.add(participant_created);
						}
						return created;
					}
				});
		return challengeUsersCreated;
	}

	@Override
	public List<ChallengeUser> get() throws SQLException {
		List <ChallengeUser> participants = challengeUserDao.queryForEq("challenge_id", challengeId);
		for(ChallengeUser participant: participants) {
			userDao.refresh(participant.getUser());
		}
		return participants;
	}

	@Override
	public boolean update() throws SQLException {
		return false; //no bulk updating
	}

	public void setParticipants(List<ChallengeUser> participants) {
		this.participants = participants;
	}
}

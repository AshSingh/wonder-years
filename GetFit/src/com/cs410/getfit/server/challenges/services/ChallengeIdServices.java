package com.cs410.getfit.server.challenges.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeUser;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

public class ChallengeIdServices implements ChallengeResourceServices {

	private Dao<Challenge, Long> challengeDao;
	private Dao<ChallengeUser, Long> challengeUserDao;
	private TransactionManager manager;
	private long challengeId;
	private Challenge challenge;

	public Dao<Challenge, Long> getChallengeDao() {
		return challengeDao;
	}

	public void setChallengeDao(Dao<Challenge, Long> challengeDao) {
		this.challengeDao = challengeDao;
	}

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

	@Override
	public List<Challenge> create() {
		return null; // cannot create challenges from inside Id service
	}

	@Override
	public List<Challenge> get() {
		List<Challenge> challenges = new ArrayList<Challenge>();
		Challenge challenge;
		try {
			challenge = challengeDao.queryForId(challengeId);
			if (challenge != null) {
				List<ChallengeUser> participants = challengeUserDao.queryForEq(
						"challenge_id", challenge.getGuid());
				if (participants != null) {
					challenge.setParticipants(participants);
				}
				challenges.add(challenge);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Challenge>();
		}
		return challenges;
	}

	@Override
	public boolean update() throws SQLException {
		challenge.setGuid(challengeId);
		Challenge oldChallenge = null;
		Integer rowsUpdated = 0;
		oldChallenge = challengeDao.queryForId(challengeId);

		// when we implement the updates we should add an update for
		// some changes
		if (oldChallenge != null) {
			if (challenge.getLocation() == null) {
				challenge.setLocation(oldChallenge.getLocation());
			}
			if (challenge.getTitle() == null) {
				challenge.setTitle(oldChallenge.getTitle());
			}
			if (challenge.getStartDate() == 0) {
				challenge.setStartDate(oldChallenge.getStartDate());
			}
			if (challenge.getEndDate() == 0) {
				challenge.setEndDate(oldChallenge.getEndDate());
			}
			if (challenge.isPrivate() == null) {
				challenge.setIsPrivate(oldChallenge.isPrivate());
			}
			rowsUpdated = manager.callInTransaction(new Callable<Integer>() {
				public Integer call() throws Exception {
					return challengeDao.update(challenge);
				}
			});
			return rowsUpdated == 1;
		}
		return false;
	}

	public void setChallengeId(long challengeId) {
		this.challengeId = challengeId;

	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}
}
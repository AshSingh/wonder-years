package com.cs410.getfit.server.challenges;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeUser;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

public class ChallengeServicesImpl implements ChallengeResourceServices {

	Dao<Challenge, Long> challengeDao;
	Dao<ChallengeUser, Long> challengeUserDao;
	TransactionManager manager;

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
	public List<Challenge> create(List<Challenge> challenges) {
		return null;
	}

	@Override
	public List<Challenge> get(long challengeId) {
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
	public boolean update(final List<Challenge> challenges, long challengeId) {
		if (challenges != null && challenges.size() == 1) {
			final Challenge updatedChallenge = challenges.get(0);
			updatedChallenge.setGuid(challengeId);
				Challenge oldChallenge = null;
				try {
					oldChallenge = challengeDao.queryForId(challengeId);
				} catch (SQLException e1) {
					e1.printStackTrace();
					return false;
				}
				// when we implement the updates we should add an update for
				// some changes
				if (oldChallenge != null) {
					if (updatedChallenge.getLocation() == null) {
						updatedChallenge.setLocation(oldChallenge.getLocation());
					}
					if (updatedChallenge.getTitle() == null) {
						updatedChallenge.setTitle(oldChallenge.getTitle());
					}
					if (updatedChallenge.getStartDate() == 0) {
						updatedChallenge.setStartDate(oldChallenge
								.getStartDate());
					} 
					if (updatedChallenge.getEndDate() == 0) {
						updatedChallenge.setEndDate(oldChallenge.getEndDate());
					} 
					if (updatedChallenge.isPrivate() == null) {
						updatedChallenge.setIsPrivate(oldChallenge.isPrivate());
					}
					try {
						    manager.callInTransaction(new Callable<Void>() {
							public Void call() throws Exception {
								challengeDao.update(updatedChallenge);
								return null;
							}
						});
					} catch (SQLException e) {
						e.printStackTrace();
						return false;
					}
					return true;
				}	
		}
		return false;
	}

}

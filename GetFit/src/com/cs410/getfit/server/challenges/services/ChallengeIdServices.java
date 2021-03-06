package com.cs410.getfit.server.challenges.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeHistory;
import com.cs410.getfit.server.models.ChallengeHistoryImpl;
import com.cs410.getfit.server.models.ChallengeUser;
import com.cs410.getfit.server.models.User;
import com.cs410.getfit.shared.NewsfeedItemType;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
/**
 * An object which handles specific challenge functionality
 * @author kiramccoan
 *
 */
public class ChallengeIdServices implements ChallengeResourceServices {

	private Dao<Challenge, Long> challengeDao;
	private Dao<ChallengeUser, Long> challengeUserDao;
	private TransactionManager manager;
	private long challengeId;
	private Challenge challenge;
	private Dao<ChallengeHistory, Long> challengeHistoryDao;

	public Dao<ChallengeHistory, Long> getChallengeHistoryDao() {
		return challengeHistoryDao;
	}

	public void setChallengeHistoryDao(
			Dao<ChallengeHistory, Long> challengeHistoryDao) {
		this.challengeHistoryDao = challengeHistoryDao;
	}

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

		if (oldChallenge != null) {	
		
		User admin = null;
		List<ChallengeUser> participants = challengeUserDao.queryForEq("challenge_id", challengeId);
		for(ChallengeUser user: participants) {
			if(user.isAdmin()) {
				admin = user.getUser();
				break;
			}
		}
		final ChallengeHistory historyItem = new ChallengeHistoryImpl(admin, challenge, NewsfeedItemType.EDIT.toString());
			rowsUpdated = manager.callInTransaction(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					Integer updated = challengeDao.update(challenge);
					
					challengeHistoryDao.create(historyItem);
					return updated;
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

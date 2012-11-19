package com.cs410.getfit.server.challenges.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;

import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeHistory;
import com.cs410.getfit.server.models.ChallengeHistoryImpl;
import com.cs410.getfit.server.models.ChallengeImpl;
import com.cs410.getfit.server.models.CompletedChallenge;
import com.cs410.getfit.shared.NewsfeedItemType;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
/**
 * An object which handles bulk completed challenge functionality
 * @author kiramccoan
 *
 */
public class CompletedChallengesServices implements CompletedChallengeResourceServices{
	private long challengeId;
	List<CompletedChallenge> c_challenges = new ArrayList<CompletedChallenge>();
	private Dao<CompletedChallenge, Long> completedChallengesDao;
	private TransactionManager manager;
	private Dao<ChallengeHistory, Long> challengeHistoryDao;
	
	public Dao<ChallengeHistory, Long> getChallengeHistoryDao() {
		return challengeHistoryDao;
	}

	public void setChallengeHistoryDao(Dao<ChallengeHistory, Long> challengeHistoryDao) {
		this.challengeHistoryDao = challengeHistoryDao;
	}
	
	public Dao<CompletedChallenge, Long> getCompletedChallengesDao() {
		return completedChallengesDao;
	}

	public void setCompletedChallengesDao(
			Dao<CompletedChallenge, Long> completedChallengesDao) {
		this.completedChallengesDao = completedChallengesDao;
	}

	public TransactionManager getTransactionManager() {
		return manager;
	}

	public void setTransactionManager(TransactionManager manager) {
		this.manager = manager;
	}

	@Override
	public List<CompletedChallenge> create() throws SQLException {
		final Challenge challenge = new ChallengeImpl();
		challenge.setGuid(challengeId);
		List<CompletedChallenge> completedChallengesCreated = new ArrayList<CompletedChallenge>();
		completedChallengesCreated = manager
				.callInTransaction(new Callable<List<CompletedChallenge>>() {
					public List<CompletedChallenge> call() throws Exception {
						List<CompletedChallenge> created = new ArrayList<CompletedChallenge>();
						for (CompletedChallenge c_challenge : c_challenges) {
							if (c_challenge.getUser() == null) { 
								return null;// dont create if no user is defined
							}
						}
						for (CompletedChallenge c_challenge: c_challenges) {
								c_challenge.setChallenge(challenge);
								long dateCompleted = Calendar.getInstance().getTimeInMillis();
								c_challenge.setDateCompleted(dateCompleted);
								CompletedChallenge completedChallenge_created = completedChallengesDao
										.createIfNotExists(c_challenge);
								createHistoryItem(completedChallenge_created);
								created.add(completedChallenge_created);
						}
						return created;
					}
				});
		return completedChallengesCreated;
	}

	private void createHistoryItem(CompletedChallenge completedChallenge) throws SQLException {
		ChallengeHistory history_item = new ChallengeHistoryImpl(completedChallenge.getUser(), completedChallenge.getChallenge(), NewsfeedItemType.COMPLETE.toString());
		challengeHistoryDao.create(history_item);
	}
	
	@Override
	public List<CompletedChallenge> get() throws SQLException {
		List<CompletedChallenge> challenges = completedChallengesDao.queryForEq("challenge_id", challengeId);
		return challenges;
	}

	@Override
	public boolean update() throws SQLException {
		return false;
	}

	public void setChallenges(List<CompletedChallenge> challenges) {
		this.c_challenges = challenges;
	}
	
	public void setChallengeId(long challengeId) {
		this.challengeId = challengeId;
	}


}

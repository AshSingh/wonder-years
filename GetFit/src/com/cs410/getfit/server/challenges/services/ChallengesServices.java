package com.cs410.getfit.server.challenges.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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

public class ChallengesServices implements ChallengeResourceServices {
	private Dao<Challenge, Long> challengeDao;
	private Dao<ChallengeUser, Long> challengeUserDao;
	private TransactionManager manager;
	private List<Challenge> challenges;
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
	public List<Challenge> create() throws SQLException {
		List<Challenge> challengesCreated = new ArrayList<Challenge>();
		challengesCreated = manager
				.callInTransaction(new Callable<List<Challenge>>() {
					public List<Challenge> call() throws Exception {
						List<Challenge> created = new ArrayList<Challenge>();
						List<ChallengeUser> participantsCreated = new ArrayList<ChallengeUser>();
						for (Challenge challenge : challenges) {
							if (challenge.getParticipants().size() != 1) { 
								return null;// dont create any if one has no admin/more than one
							}
						}
						for (Challenge challenge : challenges) {
							if (challenge.getParticipants().size() == 1) { 
								Challenge dbChallenge = challengeDao
									.createIfNotExists(challenge);
								ChallengeUser participant = challenge
										.getParticipants().get(0);
								participant.setAdmin(true);
								participant.setChallenge(dbChallenge);
								participant.setDateJoined(Calendar
										.getInstance().getTimeInMillis());
								ChallengeUser participant_created = challengeUserDao
										.createIfNotExists(participant);
								participantsCreated.add(participant_created);
								dbChallenge.setParticipants(participantsCreated);
								created.add(dbChallenge);
								
								createHistoryItem(dbChallenge, participant.getUser());
							} 
						}
						return created;
					}
				});
		return challengesCreated;
	}
	private void createHistoryItem(Challenge challenge, User admin) throws SQLException {
		ChallengeHistory history_item = new ChallengeHistoryImpl(admin, challenge, NewsfeedItemType.CREATE.toString());
		challengeHistoryDao.create(history_item);
	}
	@Override
	public List<Challenge> get() throws SQLException {
			List<Challenge> challenges = challengeDao.query(challengeDao.queryBuilder().orderBy("title", true).prepare());
			for (Challenge challenge : challenges) {
				List<ChallengeUser> participants = challengeUserDao.queryForEq(
						"challenge_id", challenge.getGuid());
				challenge.setParticipants(participants);
			}
			Collections.sort(challenges, new Comparator<Challenge>() {
				public int compare(Challenge o1, Challenge o2) {
					return o1.getTitle().compareToIgnoreCase(o2.getTitle());
				}
			});
			return challenges;
	}

	@Override
	public boolean update() {
		return false; // For now no bulk updates
	}

	public void setChallenges(List<Challenge> challenges) {
		this.challenges = challenges;
	}
	public List<Challenge> getChallenges() {
		return challenges;
	}
}

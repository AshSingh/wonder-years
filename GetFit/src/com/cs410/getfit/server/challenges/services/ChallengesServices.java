package com.cs410.getfit.server.challenges.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeUser;
import com.ibm.icu.util.Calendar;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

public class ChallengesServices implements ChallengeResourceServices {
	private Dao<Challenge, Long> challengeDao;
	private Dao<ChallengeUser, Long> challengeUserDao;
	private TransactionManager manager;
	private List<Challenge> challenges;

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
							} 
						}
						return created;
					}
				});
		return challengesCreated;
	}

	@Override
	public List<Challenge> get() throws SQLException {
			List<Challenge> challenges = challengeDao.queryForAll();
			for (Challenge challenge : challenges) {
				List<ChallengeUser> participants = challengeUserDao.queryForEq(
						"challenge_id", challenge.getGuid());
				challenge.setParticipants(participants);
			}
			return challenges;
	}

	@Override
	public boolean update() {
		return false; // For now no bulk updates
	}

	public void setChallenges(List<Challenge> challenges) {
		this.challenges = challenges;
	}
}

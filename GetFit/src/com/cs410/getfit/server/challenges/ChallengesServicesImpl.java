package com.cs410.getfit.server.challenges;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeUser;
import com.ibm.icu.util.Calendar;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

public class ChallengesServicesImpl implements ChallengeResourceServices {
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
	public List<Challenge> create(final List<Challenge> challenges) {
		List<Challenge> challengesCreated = new ArrayList<Challenge>();
		try {
			challengesCreated = manager.callInTransaction(
					new Callable<List<Challenge>>() {
						public List<Challenge> call() throws Exception {
							List<Challenge> created = new ArrayList<Challenge>();
							List<ChallengeUser> participantsCreated = new ArrayList<ChallengeUser>();
							
							for (Challenge challenge : challenges) {
								Challenge dbChallenge = challengeDao.createIfNotExists(challenge);
								
								for(ChallengeUser participant: challenge.getParticipants()) {
									participant.setChallenge(dbChallenge);
									participant.setDateJoined(Calendar.getInstance().getTimeInMillis());
									ChallengeUser participant_created = challengeUserDao.createIfNotExists(participant);
									participantsCreated.add(participant_created);
								}
								
								dbChallenge.setParticipants(participantsCreated);
								created.add(dbChallenge);
							}
							return created;
						}
					});
		} catch (SQLException e) {
			e.printStackTrace(); 
			return null;
		}
		return challengesCreated;
	}

	@Override
	public List<Challenge> get(long challengeId){
		try {
			List<Challenge> challenges = challengeDao.queryForAll();
			for(Challenge challenge: challenges) {
				List<ChallengeUser> participants = challengeUserDao.queryForEq("challenge", challenge.getGuid());
				challenge.setParticipants(participants);
			}
			return challenges;
		} catch (SQLException e) {
			return new ArrayList<Challenge>();
		}
	}

	@Override //TODO GET OBJECTS FIRST>> COMPARE WITH TO FIND DIFFERENCE> UPDATE DIFFERENCE!!
	public boolean update(final List<Challenge> challenges, long challengeId){
		try {
			manager.callInTransaction(
					new Callable<Void>() {
						public Void call() throws Exception {
							for (Challenge challenge : challenges) {
								challengeDao.update(challenge);
								for(ChallengeUser participant: challenge.getParticipants()) {
									challengeUserDao.update(participant);
								}
							}
							return null;
						}
					});
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

}

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
	public List<Challenge> get(long challengeId){
		List<Challenge> challenges = new ArrayList<Challenge>();
		Challenge challenge;
		try {
			challenge = challengeDao.queryForId(challengeId);
			List <ChallengeUser> participants = challengeUserDao.queryForEq("challenge", challenge.getGuid());
			challenge.setParticipants(participants);
			if(challenge != null)
				challenges.add(challenge);
		} catch (SQLException e) {
			return new ArrayList<Challenge>();
		}
		return new ArrayList<Challenge>();
	}

	@Override //TODO GET OBJECT FIRST>> COMPARE WITH TO FIND DIFFERENCE> UPDATE DIFFERENCE!!
	public boolean update(final List<Challenge> challenges, long challengeId) {
		if(challenges.size() == 1) {
			try {
				if(challengeDao.queryForId(challengeId) != null){
					manager.callInTransaction(new Callable<Void>() {
								public Void call() throws Exception {
										challengeDao.update(challenges.get(0));
										for(ChallengeUser participant: challenges.get(0).getParticipants()) {
											challengeUserDao.update(participant);
										}
									return null;
								}
							});
					return true;
				}
				return false;
			} catch (SQLException e) {
				return false;
			}
		} 
		return false;
	}

}

package com.cs410.getfit.server.challenges;

import com.cs410.getfit.shared.Challenge;
import com.j256.ormlite.dao.Dao;

public class ChallengesServicesImpl implements ChallengesServices {
	private Dao<Challenge, Long> challengeDao;

	public Dao<Challenge, Long> getChallengeDao() {
		return challengeDao;
	}

	public void setChallengeDao(Dao<Challenge, Long> challengeDao) {
		this.challengeDao = challengeDao;
	}
}

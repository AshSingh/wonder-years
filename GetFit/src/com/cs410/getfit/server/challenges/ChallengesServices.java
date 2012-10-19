package com.cs410.getfit.server.challenges;

import com.cs410.getfit.shared.Challenge;
import com.j256.ormlite.dao.Dao;

public interface ChallengesServices {
	Dao<Challenge, Long> getChallengeDao();

	void setChallengeDao(Dao<Challenge, Long> challengeDao);
}

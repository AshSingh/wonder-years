package com.cs410.getfit.server.users;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeUser;
import com.j256.ormlite.dao.Dao;

public class UserChallengesServices {
	private long userId;
	private Dao<ChallengeUser, Long> challengeUserDao;
	private Dao<Challenge, Long> challengeDao;

	/**
	 * return the challenges that the set user is a part of.
	 * @return
	 * @throws SQLException
	 */
	public List<Challenge> getChallenges() throws SQLException {
	List<Challenge> challenges = new ArrayList<Challenge>();
			List<ChallengeUser> participatingIn = challengeUserDao.queryForEq(
					"user_id", userId);
			for (ChallengeUser challengeUser : participatingIn) {
				challengeDao.refresh(challengeUser.getChallenge()); //bring in all the information about the challenge
				challenges.add(challengeUser.getChallenge());
			}
			return challenges;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Dao<ChallengeUser, Long> getChallengeUserDao() {
		return challengeUserDao;
	}

	public void setChallengeUserDao(Dao<ChallengeUser, Long> challengeUserDao) {
		this.challengeUserDao = challengeUserDao;
	}

	public Dao<Challenge, Long> getChallengeDao() {
		return challengeDao;
	}

	public void setChallengeDao(Dao<Challenge, Long> challengeDao) {
		this.challengeDao = challengeDao;
	}

}

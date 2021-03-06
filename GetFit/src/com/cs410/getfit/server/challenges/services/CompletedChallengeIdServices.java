package com.cs410.getfit.server.challenges.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.server.models.CompletedChallenge;
import com.j256.ormlite.dao.Dao;
/**
 * An object which handles specific completed challenge functionality
 * @author kiramccoan
 *
 */
public class CompletedChallengeIdServices implements CompletedChallengeResourceServices {
	private long completedChallengeId;
	private Dao<CompletedChallenge, Long> completedChallengesDao;

	public Dao<CompletedChallenge, Long> getCompletedChallengesDao() {
		return completedChallengesDao;
	}

	public void setCompletedChallengesDao(
			Dao<CompletedChallenge, Long> completedChallengesDao) {
		this.completedChallengesDao = completedChallengesDao;
	}

	@Override
	public List<CompletedChallenge> create() throws SQLException {
		return null;
	}

	@Override
	public List<CompletedChallenge> get() throws SQLException {
		List<CompletedChallenge> c_challenges = new ArrayList<CompletedChallenge>();
		CompletedChallenge c_challenge;
		c_challenge = completedChallengesDao.queryForId(completedChallengeId);
		c_challenges.add(c_challenge);
		return c_challenges;
	}

	@Override
	public boolean update() throws SQLException {
		return false; //cant update a completed challenge
	}

	public void setCompletedChallengeId(long challengeId) {
		this.completedChallengeId = challengeId;
	}

}

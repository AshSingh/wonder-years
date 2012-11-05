package com.cs410.getfit.server.challenges.services;

import java.sql.SQLException;
import java.util.List;

import com.cs410.getfit.server.models.CompletedChallenge;

public interface CompletedChallengeResourceServices {

	/**
	 * create the passed in challenges
	 * @throws SQLException 
	 */
	List<CompletedChallenge> create() throws SQLException;

	/**
	 * returns a list of challenges determined by the service
	 * @return
	 * @throws SQLException 
	 */
	List<CompletedChallenge> get() throws SQLException;

	boolean update() throws SQLException;
}

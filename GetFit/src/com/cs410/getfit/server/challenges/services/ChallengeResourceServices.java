package com.cs410.getfit.server.challenges.services;

import java.sql.SQLException;
import java.util.List;

import com.cs410.getfit.server.models.Challenge;

public interface ChallengeResourceServices {

	/**
	 * create the passed in challenges
	 * @throws SQLException 
	 */
	List<Challenge> create() throws SQLException;

	/**
	 * returns a list of challenges determined by the service
	 * @return
	 * @throws SQLException 
	 */
	List<Challenge> get() throws SQLException;
	
	/**
	 * @return true if the challenge is updated
	 * @throws SQLException
	 */
	boolean update() throws SQLException;
}

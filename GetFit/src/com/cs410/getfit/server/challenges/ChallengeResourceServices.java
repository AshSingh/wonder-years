package com.cs410.getfit.server.challenges;

import java.sql.SQLException;
import java.util.List;

import org.apache.http.MethodNotSupportedException;

import com.cs410.getfit.server.models.Challenge;

public interface ChallengeResourceServices {

	/**
	 * create the passed in challenges
	 * @param challenges
	 * @throws MethodNotSupportedException
	 * @throws SQLException 
	 */
	List<Challenge> create(List<Challenge> challenges) throws SQLException;

	/**
	 * returns a list of challenges determined by the service
	 * @param challengeId
	 * @return
	 * @throws SQLException 
	 */
	List<Challenge> get(long challengeId) throws SQLException;

	boolean update(List<Challenge> challenges, long challengeId) throws SQLException;
}

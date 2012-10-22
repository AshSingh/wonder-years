package com.cs410.getfit.server.challenges;

import java.sql.SQLException;
import java.util.List;

import org.apache.http.MethodNotSupportedException;

import com.cs410.getfit.shared.Challenge;

public interface ChallengeResourceServices {

	/**
	 * create the passed in challenges
	 * @param challenges
	 * @throws MethodNotSupportedException
	 * @throws SQLException 
	 */
	boolean create(List<Challenge> challenges) throws MethodNotSupportedException;

	/**
	 * returns a list of challenges determined by the service
	 * @param challengeId
	 * @return
	 * @throws SQLException 
	 */
	List<Challenge> get(long challengeId);

	boolean update(List<Challenge> challenges, long challengeId);
}

package com.cs410.getfit.server.challenges.services;

import java.sql.SQLException;
import java.util.List;

import com.cs410.getfit.server.models.ChallengeUser;
/**
 * Represents all the services that can be done on participants
 * @author kiramccoan
 *
 */
public interface ParticipantResourceServices {
	/**
	 * create the participants set in this service
	 * 
	 * @throws SQLException
	 */
	List<ChallengeUser> create() throws SQLException;

	/**
	 * returns a list of participants determined by the service
	 * 
	 * @return
	 * @throws SQLException
	 */
	List<ChallengeUser> get() throws SQLException;

	/**
	 * update the list ofparticipants set in the service
	 * 
	 * @return
	 * @throws SQLException
	 */
	boolean update() throws SQLException;
	
	/**
	 * unsubscribe the participant from the challenge
	 */
	boolean delete() throws SQLException;
}

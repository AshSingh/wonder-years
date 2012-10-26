package com.cs410.getfit.server.challenges;

import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeUser;
import com.j256.ormlite.dao.Dao;

@RunWith(JMock.class)
public class ChallengeServicesTest {
	/**
	 * List<Challenge> challenges = new ArrayList<Challenge>();
		Challenge challenge;
		try {
			challenge = challengeDao.queryForId(challengeId);
			System.out.println(challenge);
			List <ChallengeUser> participants = challengeUserDao.queryForEq("challenge_id", challenge.getGuid());
			if(challenge != null) {
				if(participants != null) {
					challenge.setParticipants(participants);
				}
				challenges.add(challenge);
			}
		} catch (SQLException e) {
			return new ArrayList<Challenge>();
		}
		return challenges;
	 */

	JUnit4Mockery context = new JUnit4Mockery();
	Dao<Challenge, Long> challengeDao;
	Dao<ChallengeUser, Long> challengeUserDao;
	
	@Before
	public void setUp(){
		challengeDao = context.mock(Dao.class); //not quite right 
	}
	
	@Test
	public void testGetExistingChallenge() {
		fail("Not yet implemented");
	}

}

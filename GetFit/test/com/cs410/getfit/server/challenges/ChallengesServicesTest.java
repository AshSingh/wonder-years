package com.cs410.getfit.server.challenges;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeUser;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

public class ChallengesServicesTest {
	JUnit4Mockery context = new JUnit4Mockery();

	Dao<Challenge, Long> challengeDao = context.mock(Dao.class,"challengeDao");
	Dao<ChallengeUser, Long> challengeUserDao = context.mock(Dao.class,"challengeUserDao");
	TransactionManager manager = new TransactionManager() {
		public <T> T callInTransaction(Callable<T> callable) throws SQLException {
			return (T)challenges;
		}
	};
	List<Challenge> challenges = new ArrayList<Challenge>();
	final Challenge challenge = context.mock(Challenge.class, "challenge");
	final Challenge challenge2 = context.mock(Challenge.class, "challenge2");
	final ChallengeUser chUser1 = context.mock(ChallengeUser.class,"chUser1");
	final ChallengeUser chUser2 = context.mock(ChallengeUser.class,"chUser2");
	
	ChallengesServicesImpl service = new ChallengesServicesImpl();
	
	final long CHGUID1 = new Long(123);
	final long CHALLENGEGUID= new Long(456);
	final String TITLE = "title1";
	final String LOCATION = "Vancouver";
	final long CHALLENGE2GUID = new Long(1234);
	final String TITLE2 = "title2";
	final String LOCATION2 = "Calgary";
	long START_TIME = System.currentTimeMillis();
	long END_TIME = System.currentTimeMillis();
	
	final List <ChallengeUser> chUsers = new ArrayList<ChallengeUser>();
	final List <ChallengeUser> chUsers2 = new ArrayList<ChallengeUser>();
	
	@Before
	public void setUp(){
		chUsers.add(chUser1);
		chUsers2.add(chUser2);

		service.setChallengeDao(challengeDao);
		service.setChallengeUserDao(challengeUserDao);
		service.setTransactionManager(manager);

	}
	
	@Test
	public void testGetAllChallengesWithParticipants() throws SQLException {
		final List<Challenge> challenges = new ArrayList<Challenge> ();
		challenges.add(challenge);
		challenges.add(challenge2);
		context.checking(new Expectations() {
			{
				oneOf(challengeDao).queryForAll(); will(returnValue(challenges));
				oneOf(challenge).getGuid(); will(returnValue(CHALLENGEGUID));
				oneOf(challenge2).getGuid(); will(returnValue(CHALLENGE2GUID));
				oneOf(challengeUserDao).queryForEq("challenge_id", CHALLENGEGUID); will(returnValue(chUsers));
				oneOf(challengeUserDao).queryForEq("challenge_id", CHALLENGE2GUID); will(returnValue(null));
				oneOf(challenge).setParticipants(chUsers);
				oneOf(challenge2).setParticipants(null);
			}
		});
		List <Challenge> returnedChallenges = service.get(-1);
		assertEquals("two challenges should be returned", 2, returnedChallenges.size());
	}
	
}

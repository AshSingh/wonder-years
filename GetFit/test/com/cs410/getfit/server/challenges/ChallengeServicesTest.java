package com.cs410.getfit.server.challenges;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.ExpectedException;

import com.cs410.getfit.server.challenges.services.ChallengeIdServices;
import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeUser;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

@RunWith(JMock.class)
public class ChallengeServicesTest {
	JUnit4Mockery context = new JUnit4Mockery();

	Dao<Challenge, Long> challengeDao = context.mock(Dao.class,"challengeDao");
	Dao<ChallengeUser, Long> challengeUserDao = context.mock(Dao.class,"challengeUserDao");
	TransactionManager manager = new TransactionManager() {
		public <T> T callInTransaction(Callable<T> callable) throws SQLException {
			return (T) new Integer(1); //stub this method out
		}
	};
	
	final Challenge challenge = context.mock(Challenge.class, "challenge");
	final Challenge challenge2 = context.mock(Challenge.class, "challenge2");
	final ChallengeUser chUser1 = context.mock(ChallengeUser.class,"chUser1");
	final ChallengeUser chUser2 = context.mock(ChallengeUser.class,"chUser2");
	
	ChallengeIdServices service = new ChallengeIdServices();
	
	final long CHGUID1 = new Long(123);
	final long CHGUID2 = new Long(456);
	final long CHALLENGEGUID= new Long(456);
	final String TITLE = "title1";
	final String LOCATION = "Vancouver";
	final long CHALLENGE2GUID = new Long(1234);
	final String TITLE2 = "title2";
	final String LOCATION2 = "Calgary";
	final String DESCRIPTION = "some description";
	final String DESCRIPTION2 = "some description take 2!";
	
	final List <ChallengeUser> chUsers = new ArrayList<ChallengeUser>();
	
	@Before
	public void setUp(){
		chUsers.add(chUser1);
		chUsers.add(chUser2);

		service.setChallengeDao(challengeDao);
		service.setChallengeUserDao(challengeUserDao);
		service.setTransactionManager(manager);

	}
	
	@Test
	public void testGetExistingChallenge() throws SQLException {
		context.checking(new Expectations () {
			{
				allowing(challenge).getGuid(); will(returnValue(CHALLENGEGUID));
				allowing(challenge).getParticipants(); will(returnValue(chUsers));
				oneOf(challengeDao).queryForId(CHALLENGEGUID); will(returnValue(challenge));
				oneOf(challengeUserDao).queryForEq("challenge_id", CHALLENGEGUID); will(returnValue(chUsers));
				oneOf(challenge).setParticipants(chUsers);
			}
		});
		service.setChallengeId(CHALLENGEGUID);
		List<Challenge> challenges = service.get();
		assertEquals("only 1 challenge should be created",1 ,challenges.size());
		Challenge actualchallenge = challenges.get(0);
		assertEquals("challenge should have challenge guid passed in", CHALLENGEGUID, actualchallenge.getGuid());
		assertEquals("challenge should have expected participants", 2, actualchallenge.getParticipants().size());
		assertEquals("challenge should have expected participant: chuser1", true, actualchallenge.getParticipants().contains(chUser1));
		assertEquals("challenge should have expected participant: chUser2", true, actualchallenge.getParticipants().contains(chUser2));
	}
	
	@ExpectedException(SQLException.class)
	@Test
	public void testSqlExceptionOnChallengeQuery() throws SQLException {
		context.checking(new Expectations () {
			{	
				allowing(challenge).getGuid(); will(returnValue(CHALLENGEGUID));
				oneOf(challengeDao).queryForId(CHALLENGEGUID); will(throwException(new SQLException()));
			}
		});
		service.setChallengeId(CHALLENGEGUID);
		List<Challenge> challenges = service.get();
		assertEquals("no challenge should be retrieved", 0, challenges.size());
	}
	
	@ExpectedException(SQLException.class)
	@Test
	public void testSqlExceptionOnParticipantsQuery() throws SQLException {
		context.checking(new Expectations () {
			{
				allowing(challenge).getGuid(); will(returnValue(CHALLENGEGUID));
				oneOf(challengeDao).queryForId(CHALLENGEGUID); will(returnValue(challenge));
				oneOf(challengeUserDao).queryForEq("challenge_id", CHALLENGEGUID); will(throwException(new SQLException()));
				
			}
		});
		service.setChallengeId(CHALLENGEGUID);
		List<Challenge> challenges = service.get();
		assertEquals("no challenge should be retrieved", 0, challenges.size());
	}
	@Test
	public void testGetNoChallenge() throws SQLException {
		context.checking(new Expectations () {
			{
				oneOf(challengeDao).queryForId(CHALLENGEGUID); will(returnValue(null));
				
			}
		});
		service.setChallengeId(CHALLENGEGUID);
		List<Challenge> challenges = service.get();
		assertEquals("no challenge should be retrieved", 0, challenges.size());
	}
	@Test
	public void testGetNoParticipants() throws SQLException {
		context.checking(new Expectations () {
			{
				allowing(challenge).getGuid(); will(returnValue(CHALLENGEGUID));
				allowing(challenge).getParticipants(); will(returnValue(new ArrayList<ChallengeUser>()));
				oneOf(challengeDao).queryForId(CHALLENGEGUID); will(returnValue(challenge));
				oneOf(challengeUserDao).queryForEq("challenge_id", CHALLENGEGUID); will(returnValue(null));
				
			}
		});
		service.setChallengeId(CHALLENGEGUID);
		List<Challenge> challenges = service.get();
		assertEquals("challenge should be retrieved", 1, challenges.size());
		assertEquals("correct challenge should be retrieved", CHALLENGEGUID, challenges.get(0).getGuid());
		assertEquals("challenge should have no participants", 0, challenges.get(0).getParticipants().size());
	}

	@Test
	public void testUpdateAllFieldsInChallenge() throws SQLException {
		context.checking(new Expectations () {
			{
				allowing(challenge).getGuid(); will(returnValue(CHALLENGE2GUID));
				allowing(challenge2).setGuid(CHALLENGE2GUID);
				allowing(challenge2).getLocation(); will(returnValue(LOCATION2));
				allowing(challenge2).isPrivate(); will(returnValue(true));
				allowing(challenge2).getDescription(); will(returnValue(DESCRIPTION2));
				allowing(challenge2).getTitle(); will(returnValue(TITLE2));
				allowing(chUser1).isAdmin(); will(returnValue(true));
				ignoring(chUser1).getUser();
				oneOf(challengeDao).queryForId(CHALLENGE2GUID); will(returnValue(challenge));
				oneOf(challengeUserDao).queryForEq("challenge_id", CHALLENGE2GUID); will(returnValue(chUsers));
			}
		});
		service.setChallenge(challenge2);
		service.setChallengeId(CHALLENGE2GUID);
		boolean updated = service.update();
		assertEquals("should return true on update", true, updated);
	}
	@Test
	public void testNoExistingChallenge() throws SQLException {
		context.checking(new Expectations () {
			{
				ignoring(challenge2).setGuid(CHALLENGE2GUID);
				oneOf(challengeDao).queryForId(CHALLENGE2GUID); will(returnValue(null));
			}
		});
		service.setChallenge(challenge2);
		service.setChallengeId(CHALLENGE2GUID);
		boolean updated = service.update();
		assertEquals("should return false on update", false, updated);
	}
}

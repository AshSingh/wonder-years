package com.cs410.getfit.server.challenges;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.ExpectedException;

import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeImpl;
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
	
	ChallengeServicesImpl service = new ChallengeServicesImpl();
	
	final long CHGUID1 = new Long(123);
	final long CHGUID2 = new Long(456);
	final long CHALLENGEGUID= new Long(456);
	final String TITLE = "title1";
	final String LOCATION = "Vancouver";
	final long CHALLENGE2GUID = new Long(1234);
	final String TITLE2 = "title2";
	final String LOCATION2 = "Calgary";
	long START_TIME = System.currentTimeMillis();
	long END_TIME = System.currentTimeMillis();
	
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
		List<Challenge>challenges = service.get(CHALLENGEGUID);
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
		List<Challenge>challenges = service.get(CHALLENGEGUID);
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
		List<Challenge>challenges = service.get(CHALLENGEGUID);
		assertEquals("no challenge should be retrieved", 0, challenges.size());
	}
	@Test
	public void testGetNoChallenge() throws SQLException {
		context.checking(new Expectations () {
			{
				oneOf(challengeDao).queryForId(CHALLENGEGUID); will(returnValue(null));
				
			}
		});
		List<Challenge>challenges = service.get(CHALLENGEGUID);
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
		List<Challenge>challenges = service.get(CHALLENGEGUID);
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
				allowing(challenge2).getStartDate(); will(returnValue(START_TIME));
				allowing(challenge2).getEndDate(); will(returnValue(END_TIME));
				allowing(challenge2).getTitle(); will(returnValue(TITLE2));
				oneOf(challengeDao).queryForId(CHALLENGE2GUID); will(returnValue(challenge));	
			}
		});
		List<Challenge> challenges = new ArrayList<Challenge>();
		challenges.add(challenge2);
		boolean updated = service.update(challenges , CHALLENGE2GUID);
		assertEquals("should return true on update", true, updated);
	}
	@Test
	public void testUpdateNoFieldsInChallenge() throws SQLException {
		Challenge emptyChallenge = new ChallengeImpl();
		context.checking(new Expectations () {
			{
				allowing(challenge).getGuid(); will(returnValue(CHALLENGEGUID));
				allowing(challenge).getLocation(); will(returnValue(LOCATION));
				allowing(challenge).isPrivate(); will(returnValue(true));
				allowing(challenge).getStartDate(); will(returnValue(START_TIME));
				allowing(challenge).getEndDate(); will(returnValue(END_TIME));
				allowing(challenge).getTitle(); will(returnValue(TITLE));
				oneOf(challengeDao).queryForId(CHALLENGEGUID); will(returnValue(challenge));	
			}
		});
		List<Challenge> challenges = new ArrayList<Challenge>();
		challenges.add(emptyChallenge);
		boolean updated = service.update(challenges , CHALLENGEGUID);
		assertEquals("should return true on update", true, updated);
		Challenge expectedChallenge = challenges.get(0);
		assertEquals("empty object should have set location", LOCATION, expectedChallenge.getLocation());
		assertEquals("empty object should have set isprivate", true, expectedChallenge.isPrivate());
		assertEquals("empty object should have set start date", START_TIME, expectedChallenge.getStartDate());
		assertEquals("empty object should have set end date", END_TIME, expectedChallenge.getEndDate());
		assertEquals("empty object should have set title", TITLE, expectedChallenge.getTitle());
	}
}

package com.cs410.getfit.server.challenges;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import com.cs410.getfit.server.challenges.services.ChallengesServices;
import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeHistory;
import com.cs410.getfit.server.models.ChallengeUser;
import com.cs410.getfit.server.models.User;
import com.j256.ormlite.dao.Dao;

public class ChallengesServicesTest {
	JUnit4Mockery context = new JUnit4Mockery();

	Dao<Challenge, Long> challengeDao = context.mock(Dao.class,"challengeDao");
	Dao<ChallengeHistory, Long>challengeHistoryDao = context.mock(Dao.class, "challengeHistory");
	Dao<ChallengeUser, Long> challengeUserDao = context.mock(Dao.class,"challengeUserDao");
	Dao<User, Long> userDao = context.mock(Dao.class,"userDao");

	List<Challenge> challenges = new ArrayList<Challenge>();
	final Challenge challenge = context.mock(Challenge.class, "challenge");
	final Challenge challenge2 = context.mock(Challenge.class, "challenge2");
	final ChallengeUser chUser1 = context.mock(ChallengeUser.class,"chUser1");
	final ChallengeUser chUser2 = context.mock(ChallengeUser.class,"chUser2");
	
	ChallengesServices service = new ChallengesServices() {
		protected List<Challenge> getChallengesOrderedByTitle() throws SQLException {
			return challenges;
		}
	};
	
	final long CHGUID1 = new Long(123);
	final long CHALLENGEGUID= new Long(456);
	final String TITLE = "title1";
	final long CHALLENGE2GUID = new Long(1234);
	final String TITLE2 = "title2";
	
	final List <ChallengeUser> chUsers = new ArrayList<ChallengeUser>();
	final List <ChallengeUser> chUsers2 = new ArrayList<ChallengeUser>();
	
	@Before
	public void setUp(){
		chUsers.add(chUser1);
		chUsers2.add(chUser2);

		service.setChallengeDao(challengeDao);
		service.setChallengeUserDao(challengeUserDao);
		
		challenges.add(challenge);
		challenges.add(challenge2);

	}
	@Test
	public void testSettingAndGettingChallengesForService() {
		List<Challenge> chall = new ArrayList<Challenge>();
		service.setChallenges(chall);
		assertEquals(chall, service.getChallenges());
	}
	
	@Test
	public void testUpdateIsAlwaysFals() {
		assertEquals(false, service.update());
	}

	@Test
	public void testSetAndGetChallengeUserDao() {
		service.setChallengeUserDao(challengeUserDao);
		assertEquals(challengeUserDao, service.getChallengeUserDao());
	}
	@Test
	public void testSetAndGetChallengeHistoryDao() {
		service.setChallengeHistoryDao(challengeHistoryDao);
		assertEquals(challengeHistoryDao, service.getChallengeHistoryDao());
	}
	@Test
	public void testSetAndGetChallengeDao() {
		service.setChallengeDao(challengeDao);
		assertEquals(challengeDao, service.getChallengeDao());
	}
	
	@Test
	public void testGet() throws SQLException {
		final List<ChallengeUser> emptyList = new ArrayList<ChallengeUser>();
		context.checking(new Expectations () {
			{
				oneOf(challenge).getGuid(); will(returnValue(CHALLENGEGUID));
				oneOf(challenge2).getGuid(); will(returnValue(CHALLENGE2GUID));
				oneOf(challengeUserDao).queryForEq(
						"challenge_id", CHALLENGEGUID); will(returnValue(emptyList));
				oneOf(challengeUserDao).queryForEq(
						"challenge_id", CHALLENGE2GUID); will(returnValue(chUsers));
				oneOf(challenge).setParticipants(emptyList);
				oneOf(challenge2).setParticipants(chUsers);
				allowing(challenge).getTitle(); will(returnValue(TITLE));
				allowing(challenge2).getTitle(); will(returnValue(TITLE2));
			}
		});
		List<Challenge>challenges =  service.get();
		assertEquals("should get two challenges", 2, challenges.size());
		assertEquals("should be sorted by title", TITLE, challenges.get(0).getTitle());
		assertEquals("should be sorted by title", TITLE2, challenges.get(1).getTitle());
	}
}

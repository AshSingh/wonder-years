package com.cs410.getfit.server.challenges;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cs410.getfit.server.challenges.services.CompletedChallengeIdServices;
import com.cs410.getfit.server.models.CompletedChallenge;
import com.j256.ormlite.dao.Dao;

@RunWith(JMock.class)
public class CompletedChallengeIdServicesTest {
	JUnit4Mockery context = new JUnit4Mockery();

	Dao<CompletedChallenge, Long> completedChallengesDao = context.mock(Dao.class,"completedChallengeDao");
	CompletedChallengeIdServices services = new CompletedChallengeIdServices();
	long challengeId = 1;
	final CompletedChallenge cChallenge = context.mock(CompletedChallenge.class);
	
	@Before
	public void setUp(){
		services.setCompletedChallengeId(challengeId);
		services.setCompletedChallengesDao(completedChallengesDao);
	}
	
	@Test
	public void testCreate() throws SQLException {
		assertEquals("create should be null", null, services.create());
	}
	@Test
	public void testUpdate() throws SQLException {
		assertEquals("update should be false", false, services.update());
	}
	
	@Test
	public void testGet() throws SQLException {
		context.checking(new Expectations () {
			{
				oneOf(completedChallengesDao).queryForId(challengeId); will(returnValue(cChallenge));
			}
		});
		List<CompletedChallenge> cChallenges = services.get();
		assertEquals("returns list of size 1", 1,cChallenges.size());
		assertEquals("returns correct challenge", cChallenge, cChallenges.get(0));
	}
}

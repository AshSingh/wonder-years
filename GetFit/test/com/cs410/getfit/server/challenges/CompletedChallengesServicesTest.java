package com.cs410.getfit.server.challenges;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cs410.getfit.server.challenges.services.CompletedChallengesServices;
import com.cs410.getfit.server.models.CompletedChallenge;
import com.j256.ormlite.dao.Dao;

@RunWith(JMock.class)
public class CompletedChallengesServicesTest {
	JUnit4Mockery context = new JUnit4Mockery();

	Dao<CompletedChallenge, Long> completedChallengesDao = context.mock(Dao.class,"completedChallengeDao");
	CompletedChallengesServices services = new CompletedChallengesServices();
	long challengeId = 1;
	final CompletedChallenge cChallenge = context.mock(CompletedChallenge.class);
	final List<CompletedChallenge> cChallengeList = new ArrayList<CompletedChallenge>();

	@Before
	public void setUp(){
		cChallengeList.add(cChallenge);
		services.setChallengeId(challengeId);
		services.setChallenges(cChallengeList);
		services.setCompletedChallengesDao(completedChallengesDao);
	}
	
	@Test
	public void testGet() throws SQLException {
		context.checking(new Expectations () {
			{
				oneOf(completedChallengesDao).queryForEq("challenge_id", challengeId); will(returnValue(cChallengeList));
			}
		});
		List<CompletedChallenge> cChallenges = services.get();
		assertEquals("returns list of size 1", 1,cChallenges.size());
		assertEquals("returns correct challenge", cChallenge, cChallenges.get(0));
	}
}

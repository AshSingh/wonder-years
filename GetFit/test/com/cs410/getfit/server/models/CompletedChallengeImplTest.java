package com.cs410.getfit.server.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CompletedChallengeImplTest {

	UserImpl user = new UserImpl();
	ChallengeImpl challenge = new ChallengeImpl();
	long dateCompleted = 1;
	long guid = 2;
	
	CompletedChallenge completed = new CompletedChallengeImpl(user, challenge, dateCompleted);
	
	@Test
	public void setChallengeTest() {
		completed.setChallenge(challenge);
		assertEquals(completed.getChallenge(), challenge);
	}

	@Test
	public void setUserTest() {
		completed.setUser(user);
		assertEquals(completed.getUser(), user);
	}
	
	@Test
	public void setDateJoinedTest() {
		completed.setDateCompleted(dateCompleted);
		assertEquals(completed.getDateCompleted(), dateCompleted);
	}
	
	@Test
	public void setGuidTest() {
		completed.setGuid(guid);
		assertEquals(completed.getGuid(), guid);
	}

}

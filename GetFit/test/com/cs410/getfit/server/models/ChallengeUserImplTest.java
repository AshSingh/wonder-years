package com.cs410.getfit.server.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ChallengeUserImplTest {

	User user = new UserImpl();
	Challenge challenge = new ChallengeImpl();
	long creationDate = 1;
	boolean isAdmin = true;
	long guid = 2;
	
	ChallengeUser chUser = new ChallengeUserImpl(user, challenge, isAdmin, creationDate);
	
	@Test
	public void setChallengeTest() {
		chUser.setChallenge(challenge);
		assertEquals(chUser.getChallenge(), challenge);
	}

	@Test
	public void setAdminTest() {
		chUser.setAdmin(true);
		assertTrue(chUser.isAdmin());
	}

	@Test
	public void setDateJoinedTest() {
		chUser.setDateJoined(creationDate);
		assertEquals(chUser.getDateJoined(), creationDate);
	}
	
	@Test
	public void setGuid() {
		chUser.setGuid(guid);
		assertEquals(chUser.getGuid(), guid);
	}
}

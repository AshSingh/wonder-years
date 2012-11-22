package com.cs410.getfit.server.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class ChallengeHistoryImplTest {

	User user = new UserImpl();
	Challenge challenge = new ChallengeImpl();
	String desc = "testdesc";
	long datemodified = 1;
	long guid = 2;

	ChallengeHistory chHistory = new ChallengeHistoryImpl(user, challenge, desc, datemodified);
	ChallengeHistory chHistory2 = new ChallengeHistoryImpl();

	@Test
	public void setGuidTest() {
		chHistory2.setGuid(guid);
		assertEquals(chHistory2.getGuid(), guid);
	}

	@Test
	public void getUserTest() {
		assertEquals(chHistory.getUser(), user);
	}
	
	@Test
	public void setUserTest() {
		chHistory2.setUser(user);
		assertEquals(chHistory2.getUser(), user);
	}
	
	@Test
	public void getChallengeTest() {
		assertEquals(chHistory.getChallenge(), challenge);
	}
	
	@Test
	public void setChallengeTest() {
		chHistory2.setChallenge(challenge);
		assertEquals(chHistory2.getChallenge(), challenge);
	}

	@Test
	public void getHistoryDescriptionTest() {
		assertEquals(chHistory.getHistoryDescription(), desc);
	}

	@Test
	public void setHistoryDescriptionTest() {
		chHistory2.setHistoryDescription(desc);
		assertEquals(chHistory2.getHistoryDescription(), desc);	
	}

	@Test
	public void getDateModifiedTest() {
		assertEquals(chHistory.getDateModified(), datemodified);
	}

	@Test
	public void setDateModifiedTest() {
		chHistory2.setDateModified(datemodified);
		assertEquals(chHistory2.getDateModified(), datemodified);
	}
}

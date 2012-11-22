package com.cs410.getfit.server.models;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ChallengeImplTest {
	
	List<ChallengeUser> participants = new ArrayList<ChallengeUser>();
	String title = "testTitle";
	boolean isPrivate = true;
	String location = "testLoc";
	String description = "testdesc";
	long guid = 1;
	
	Challenge challenge = new ChallengeImpl(); 

	@Test
	public void setTitleTest() {
		challenge.setTitle(title);
		assertEquals(challenge.getTitle(), title);
	}
	
	@Test
	public void setIsPrivateTest() {
		challenge.setIsPrivate(isPrivate);
		assertTrue(challenge.isPrivate());
	}
	
	@Test
	public void setLocationTest() {
		challenge.setLocation(location);
		assertEquals(challenge.getLocation(), location);
	}
	
	@Test
	public void setParticipantsTest() {
		challenge.setParticipants(participants);
		assertEquals(challenge.getParticipants(), participants);
	}
	
	@Test
	public void setGuidTest() {
		challenge.setGuid(guid);
		assertEquals(challenge.getGuid(), guid);
	}
	
	@Test
	public void setDescriptionTest() {
		challenge.setDescription(description);
		assertEquals(challenge.getDescription(), description);
	}
}

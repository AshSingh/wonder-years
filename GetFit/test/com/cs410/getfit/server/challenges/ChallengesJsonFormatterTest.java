package com.cs410.getfit.server.challenges;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.cs410.getfit.shared.Challenge;
import com.cs410.getfit.shared.ChallengeImpl;

public class ChallengesJsonFormatterTest {

	private String challengesJsonString = "";
	List <Challenge> challenges;
	ChallengesJsonFormatter jsonFormatter = new ChallengesJsonFormatter();
	
	@Before
	public void setUp() {

		challenges = new ArrayList<Challenge>();
		Calendar cal = Calendar.getInstance();
		cal.set(1989, Calendar.JANUARY, 01,0,0,0);
		final Date startDate = cal.getTime();;
		cal.set(1990, Calendar.DECEMBER, 01,0,0,0);
		final Date endDate = cal.getTime();

		challengesJsonString = "{\"challenges\":["+
				"{\"guid\":12345,\"title\":\"Go Pro!\",\"startdate\":"+startDate.getTime()+",\"enddate\":"+endDate.getTime()+",\"location\":\"Vancouver\",\"isprivate\":true},"+
				"{\"guid\":7897,\"title\":\"Be Fit\",\"startdate\":"+startDate.getTime()+",\"enddate\":"+endDate.getTime()+",\"location\":\"Calgary\",\"isprivate\":false}"+
				"]}";
		Challenge challenge = new ChallengeImpl("Go Pro!", true, "Vancouver", startDate.getTime(), endDate.getTime());
		challenge.setGuid(new Long(12345));
		Challenge challenge1 = new ChallengeImpl("Be Fit", false, "Calgary", startDate.getTime(), endDate.getTime());
		challenge1.setGuid(new Long(7897));
		
		challenges.add(challenge);
		challenges.add(challenge1);
	}
	
	@Test
	public void testGetJsonFormattedUserArray() {
		String jsonFormattedUsers = jsonFormatter.getJSONFormattedStringOfResource(challenges);
		assertEquals(challengesJsonString,jsonFormattedUsers);
	}
	@Test
	public void testGetUsersFromJsonFormattedString() {
		List <Challenge> actualChallenges = jsonFormatter.getResourcesFromJSONFormattedString(challengesJsonString);
		assertEquals(2, actualChallenges.size());
		Challenge challenge1 = actualChallenges.get(0);
		Challenge expectedChallenge1 = challenges.get(0);
		assertEquals(expectedChallenge1.getGuid(), challenge1.getGuid());
		assertEquals(expectedChallenge1.getTitle(), challenge1.getTitle());
		assertEquals(expectedChallenge1.getStartDate(), challenge1.getStartDate());
		assertEquals(expectedChallenge1.getEndDate(), challenge1.getEndDate());
		assertEquals(expectedChallenge1.getLocation(), challenge1.getLocation());
		assertEquals(expectedChallenge1.isPrivate(), challenge1.isPrivate());
		
		Challenge challenge2 = actualChallenges.get(1);
		Challenge expectedChallenge2 = challenges.get(1);
		assertEquals(expectedChallenge2.getGuid(), challenge2.getGuid());
		assertEquals(expectedChallenge2.getTitle(), challenge2.getTitle());
		assertEquals(expectedChallenge2.getStartDate(), challenge2.getStartDate());
		assertEquals(expectedChallenge2.getEndDate(), challenge2.getEndDate());
		assertEquals(expectedChallenge2.getLocation(), challenge2.getLocation());
		assertEquals(expectedChallenge2.isPrivate(), challenge2.isPrivate());
	}
	
	

}

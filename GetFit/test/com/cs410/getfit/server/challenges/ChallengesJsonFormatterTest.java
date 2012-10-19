package com.cs410.getfit.server.challenges;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cs410.getfit.shared.Challenge;

@RunWith(JMock.class)
public class ChallengesJsonFormatterTest {

	private Mockery context = new Mockery();
	private String challengesJsonString = "";
	List <Challenge> challenges;
	
	@Before
	public void setUp() {

		challenges = new ArrayList<Challenge>();
		final Challenge challenge1 = context.mock(Challenge.class, "challenge1");
		final Challenge challenge2 = context.mock(Challenge.class, "challenge2");
		Calendar cal = Calendar.getInstance();
		cal.set(1989, Calendar.JANUARY, 01,0,0,0);
		final Date startDate = cal.getTime();;
		cal.set(1990, Calendar.DECEMBER, 01,0,0,0);
		final Date endDate = cal.getTime();

		challengesJsonString = "{\"challenges\":["+
				"{\"guid\":12345,\"title\":\"Go Pro!\",\"startdate\":"+startDate.getTime()+",\"enddate\":"+endDate.getTime()+",\"location\":\"Vancouver\",\"isprivate\":true},"+
				"{\"guid\":7897,\"title\":\"Be Fit\",\"startdate\":"+startDate.getTime()+",\"enddate\":"+endDate.getTime()+",\"location\":\"Calgary\",\"isprivate\":false}"+
				"]}";
		context.checking(new Expectations() {
			{
				allowing(challenge1).getGuid(); will(returnValue(new Long(12345)));
				allowing(challenge1).getTitle(); will(returnValue("Go Pro!"));
				allowing(challenge1).getStartDate(); will(returnValue(startDate.getTime()));
				allowing(challenge1).getEndDate(); will(returnValue(endDate.getTime()));
				allowing(challenge1).getLocation(); will(returnValue("Vancouver"));
				allowing(challenge1).isPrivate(); will(returnValue(true));

				allowing(challenge2).getGuid(); will(returnValue(new Long(7897)));
				allowing(challenge2).getTitle(); will(returnValue("Be Fit"));
				allowing(challenge2).getStartDate(); will(returnValue(startDate.getTime()));
				allowing(challenge2).getEndDate(); will(returnValue(endDate.getTime()));
				allowing(challenge2).getLocation(); will(returnValue("Calgary"));
				allowing(challenge2).isPrivate(); will(returnValue(false));
			}
		});
		challenges.add(challenge1);
		challenges.add(challenge2);
	}
	
	@Test
	public void testGetJsonFormattedUserArray() {
		String jsonFormattedUsers = ChallengesJsonFormatter.getJSONFormattedStringOfChallenges(challenges);
		assertEquals(challengesJsonString,jsonFormattedUsers);
	}
	@Test
	public void testGetUsersFromJsonFormattedString() {
		List <Challenge> actualChallenges = ChallengesJsonFormatter.getChallengesFromJSONFormattedString(challengesJsonString);
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

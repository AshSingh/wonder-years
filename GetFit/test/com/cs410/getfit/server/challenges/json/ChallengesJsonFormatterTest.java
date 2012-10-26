package com.cs410.getfit.server.challenges.json;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cs410.getfit.server.challenges.json.ChallengesJsonFormatter;
import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeUser;
import com.cs410.getfit.server.models.User;

@RunWith(JMock.class)
public class ChallengesJsonFormatterTest {

	private String challengesIncomingJsonString;
	private String challengesOutgoingJsonString;
	List <Challenge> challenges;
	List <ChallengeUser> chUsers;
	JUnit4Mockery context = new JUnit4Mockery();
	ChallengesJsonFormatter jsonFormatter = new ChallengesJsonFormatter();
	
	@Before
	public void setUp() {
		final String USERNAME = "username1";
		final String USERNAME2 = "username2";
		final Long CHGUID1 = new Long(123);
		final Long CHGUID2 = new Long(456);
		final Long CHALLENGEGUID = new Long(456);
		final String CHALLENGE_TITLE = "title1";
		final String CHALLENGE_LOC = "Vancouver";
		final Long CHALLENGE2GUID = new Long(1234);
		final String CHALLENGE2_TITLE = "title2";
		final String CHALLENGE2_LOC = "Calgary";

		final ChallengeUser chUser1 = context.mock(ChallengeUser.class,"chUser1");
		final ChallengeUser chUser2 = context.mock(ChallengeUser.class,"chUser2");
		final User user = context.mock(User.class, "user");
		final User user2 = context.mock(User.class, "user2");
		final Challenge challenge = context.mock(Challenge.class, "challenge1");
		final Challenge challenge1 = context.mock(Challenge.class, "challenge2");
		
		chUsers = new ArrayList<ChallengeUser>();
		chUsers.add(chUser1);
		chUsers.add(chUser2);
		challenges = new ArrayList<Challenge>();
		challenges.add(challenge);
		challenges.add(challenge1);
		
		Calendar cal = Calendar.getInstance();
		cal.set(2012, Calendar.JANUARY, 01,0,0,0);
		final Date startDate = cal.getTime();;
		cal.set(2013, Calendar.DECEMBER, 01,0,0,0);
		final Date endDate = cal.getTime();

		final Long START_TIME = startDate.getTime();
		final Long END_TIME = endDate.getTime();
		
		context.checking(new Expectations() {
			{
				allowing(user).getUsername(); will(returnValue(USERNAME));
				allowing(user2).getUsername(); will(returnValue(USERNAME2));
				
				allowing(chUser1).getGuid(); will(returnValue(CHGUID1));
				allowing(chUser1).getUser(); will(returnValue(user));
				allowing(chUser1).isAdmin(); will(returnValue(true));
				allowing(chUser1).isSubscribed(); will(returnValue(true));

				allowing(chUser2).getGuid(); will(returnValue(CHGUID2));
				allowing(chUser2).getUser(); will(returnValue(user2));
				allowing(chUser2).isAdmin(); will(returnValue(false));
				allowing(chUser2).isSubscribed(); will(returnValue(false));
				
				allowing(challenge).getGuid(); will(returnValue(CHALLENGEGUID));
				allowing(challenge).getParticipants(); will(returnValue(chUsers));
				allowing(challenge).getTitle(); will(returnValue(CHALLENGE_TITLE));
				allowing(challenge).getLocation(); will(returnValue(CHALLENGE_LOC));
				allowing(challenge).getStartDate(); will(returnValue(START_TIME));
				allowing(challenge).getEndDate(); will(returnValue(END_TIME));
				allowing(challenge).isPrivate(); will(returnValue(true));
				
				allowing(challenge1).getGuid(); will(returnValue(CHALLENGE2GUID));
				allowing(challenge1).getTitle(); will(returnValue(CHALLENGE2_TITLE));
				allowing(challenge1).getLocation(); will(returnValue(CHALLENGE2_LOC));
				allowing(challenge1).getStartDate(); will(returnValue(START_TIME));
				allowing(challenge1).getEndDate(); will(returnValue(END_TIME));
				allowing(challenge1).isPrivate(); will(returnValue(false));
				allowing(challenge1).getParticipants(); will(returnValue(new ArrayList<ChallengeUser>()));
			}
		});

		challengesIncomingJsonString = "{\"challenges\":["+
				"{\"info\":{\"title\":\""+CHALLENGE_TITLE+"\"," +
				"\"startdate\":"+START_TIME+"," +
				"\"enddate\":"+END_TIME+"," +
				"\"location\":\""+CHALLENGE_LOC+"\"," +
				"\"isprivate\":true}," +
				"\"admin\": \""+USERNAME+"\"},"+
				"{\"info\":{\"title\":\""+CHALLENGE2_TITLE+"\"," +
				"\"startdate\":"+START_TIME+"," +
				"\"enddate\":"+END_TIME+"," +
				"\"location\":\""+CHALLENGE2_LOC+"\"," +
				"\"isprivate\":false}," +
				"\"admin\": \""+USERNAME2+"\"}"+
				"]}";
		
		challengesOutgoingJsonString = "{\"challenges\":["+
				"{\"info\":{\"title\":\""+CHALLENGE_TITLE+"\"," +
				"\"startdate\":"+START_TIME+"," +
				"\"enddate\":"+END_TIME+"," +
				"\"location\":\""+CHALLENGE_LOC+"\"," +
				"\"isprivate\":true}," +
				"\"links\":[{\"rel\":\"self\",\"uri\":\"/challenges/"+CHALLENGEGUID+"\",\"type\":\"challenge\"}," +
							"{\"rel\":\"/challenges/"+CHALLENGEGUID+"\",\"uri\":\"/participants\",\"type\":\"participants\"}" +
							"]}," +
				"{\"info\":{\"title\":\""+CHALLENGE2_TITLE+"\"," +
				"\"startdate\":"+START_TIME+"," +
				"\"enddate\":"+END_TIME+"," +
				"\"location\":\""+CHALLENGE2_LOC+"\"," +
				"\"isprivate\":false},"+
				"\"links\":[{\"rel\":\"self\",\"uri\":\"/challenges/"+CHALLENGE2GUID+"\",\"type\":\"challenge\"}," +
							"{\"rel\":\"/challenges/"+CHALLENGE2GUID+"\",\"uri\":\"/participants\",\"type\":\"participants\"}" +
							"]}" +
				"]}";
	}
	
	@Test
	public void testGetJsonFormattedUserArray() {
		String jsonFormattedChallenges = jsonFormatter.getJSONFormattedStringOfResource(challenges);
		assertEquals(challengesOutgoingJsonString,jsonFormattedChallenges);
	}
	@Test
	public void testGetChallengesFromJsonFormattedString() {
		List <Challenge> actualChallenges = jsonFormatter.getResourcesFromJSONFormattedString(challengesIncomingJsonString);
		assertEquals(2, actualChallenges.size());
		Challenge challenge1 = actualChallenges.get(0);
		Challenge expectedChallenge1 = challenges.get(0);
		assertEquals(0, challenge1.getGuid());
		assertEquals(expectedChallenge1.getTitle(), challenge1.getTitle());
		assertEquals(expectedChallenge1.getStartDate(), challenge1.getStartDate());
		assertEquals(expectedChallenge1.getEndDate(), challenge1.getEndDate());
		assertEquals(expectedChallenge1.getLocation(), challenge1.getLocation());
		assertEquals(expectedChallenge1.isPrivate(), challenge1.isPrivate());
		assertEquals(1,challenge1.getParticipants().size());
		assertEquals(true, challenge1.getParticipants().get(0).isAdmin());
		assertEquals(true, challenge1.getParticipants().get(0).isSubscribed());
		//assert that expected challenge admin is the admin we get back
		
		Challenge challenge2 = actualChallenges.get(1);
		Challenge expectedChallenge2 = challenges.get(1);
		assertEquals(0, challenge2.getGuid());
		assertEquals(expectedChallenge2.getTitle(), challenge2.getTitle());
		assertEquals(expectedChallenge2.getStartDate(), challenge2.getStartDate());
		assertEquals(expectedChallenge2.getEndDate(), challenge2.getEndDate());
		assertEquals(expectedChallenge2.getLocation(), challenge2.getLocation());
		assertEquals(expectedChallenge2.isPrivate(), challenge2.isPrivate());
		assertEquals(1,challenge2.getParticipants().size());
		assertEquals(true, challenge2.getParticipants().get(0).isAdmin());
		assertEquals(true, challenge2.getParticipants().get(0).isSubscribed());
	}
	
	

}

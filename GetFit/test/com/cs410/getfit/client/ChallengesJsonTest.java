package com.cs410.getfit.client;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.cs410.getfit.client.json.ChallengesJsonFormatter;
import com.cs410.getfit.server.challenges.json.ChallengeInfoJsonModel;
import com.google.gwt.junit.client.GWTTestCase;

public class ChallengesJsonTest extends GWTTestCase{

	ArrayList <ChallengeInfoJsonModel> challenges;
	final int CHALLENGE_ADMIN = 1;
	
	private String challengeJsonPrefix;
	private String challengeJsonTitle;
	private String challengeJsonStart;
	private String challengeJsonEnd;
	private String challengeJsonLoc;
	private String challengeJsonPrivate;
	private String challengeJsonAdmin;
	
	@Before
	public void gwtSetUp() throws Exception {
		// challenge details
		final String CHALLENGE_TITLE = "title1";
		final String CHALLENGE_LOC = "Vancouver";
		
		Date startDate = new Date();
		Date endDate = new Date();

		final Long START_TIME = startDate.getTime();
		final Long END_TIME = endDate.getTime();
		
		final Boolean CHALLENGE_PRIVATE = true;

		ChallengeInfoJsonModel challenge1 = new ChallengeInfoJsonModel();
		challenge1.setTitle(CHALLENGE_TITLE);
		challenge1.setStartdate(START_TIME);
		challenge1.setEnddate(END_TIME);
		challenge1.setLocation(CHALLENGE_LOC);
		challenge1.setIsprivate(CHALLENGE_PRIVATE);
		
		challenges = new ArrayList<ChallengeInfoJsonModel>();
		challenges.add(challenge1);
				
		// strings to verify
		challengeJsonPrefix = "{\"challenges\":[{\"info\":{";
		challengeJsonTitle = "\"title\":\""+CHALLENGE_TITLE+"\"";
		challengeJsonStart = "\"startdate\":"+START_TIME;
		challengeJsonEnd = "\"enddate\":"+END_TIME;
		challengeJsonLoc = "\"location\":\""+CHALLENGE_LOC+"\"";
		challengeJsonPrivate = "\"isprivate\":\""+Boolean.toString(CHALLENGE_PRIVATE)+"\"";
		challengeJsonAdmin = "}, \"admin\":"+ Integer.toString(CHALLENGE_ADMIN) + "}]";
	}
	
	@Test
	public void testToJson() {
		String json = ChallengesJsonFormatter.formatChallengeJsonInfo(challenges, CHALLENGE_ADMIN);
		
		// order of json objects not guaranteed - check for substrings
		assertTrue(json.contains(challengeJsonPrefix));
		assertTrue(json.contains(challengeJsonTitle));
		assertTrue(json.contains(challengeJsonStart));
		assertTrue(json.contains(challengeJsonEnd));
		assertTrue(json.contains(challengeJsonLoc));
		assertTrue(json.contains(challengeJsonPrivate));
		assertTrue(json.contains(challengeJsonAdmin));
	}

	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}

}

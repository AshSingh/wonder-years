package com.cs410.getfit.client;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.cs410.getfit.client.json.ChallengesJsonFormatter;
import com.cs410.getfit.server.challenges.json.ChallengeInfoJsonModel;
import com.cs410.getfit.server.challenges.json.IncomingChallengeJsonModel;
import com.cs410.getfit.server.challenges.json.OutgoingChallengeJsonModel;
import com.cs410.getfit.server.json.ResourceLink;
import com.google.gwt.junit.client.GWTTestCase;

public class ChallengesJsonTest extends GWTTestCase{

	ArrayList <ChallengeInfoJsonModel> challenges;
	final Long CHALLENGE_ADMIN = new Long(1);
	
	// formatting strings
	private String challengeJsonPrefix;
	private String challengeJsonTitle;
	private String challengeJsonLoc;
	private String challengeJsonPrivate;
	private String challengeJsonAdmin;
	
	// parsing string
	private String challengesIncomingJsonString;
	
	// challenge details
	final String CHALLENGE_TITLE = "title1";
	final String CHALLENGE_LOC = "Vancouver";
	final Boolean CHALLENGE_PRIVATE = true;

	final String CHALLENGE2_TITLE = "title2";
	final String CHALLENGE2_LOC = "Calgary";
	final Boolean CHALLENGE2_PRIVATE = false;
	
	final String CHALLENGE_SELF_REL = "self";
	final String CHALLENGE_REL = "/challenges/";
	final String CHALLENGE_URI = "/participants";	
	final String CHALLENGE_TYPE = "challenge";
	final String PARTICIPANT_TYPE = "participants";
	final long CHALLENGEGUID = new Long(456);
	final long CHALLENGE2GUID = new Long(1234);
	
	@Before
	public void gwtSetUp() throws Exception {
		ChallengeInfoJsonModel challenge1 = new ChallengeInfoJsonModel();
		challenge1.setTitle(CHALLENGE_TITLE);
		challenge1.setLocation(CHALLENGE_LOC);
		challenge1.setIsprivate(CHALLENGE_PRIVATE);
		
		challenges = new ArrayList<ChallengeInfoJsonModel>();
		challenges.add(challenge1);
				
		// strings to verify
		challengeJsonPrefix = "{\"challenges\":[{\"info\":{";
		challengeJsonTitle = "\"title\":\""+CHALLENGE_TITLE+"\"";
		challengeJsonLoc = "\"location\":\""+CHALLENGE_LOC+"\"";
		challengeJsonPrivate = "\"isprivate\":"+Boolean.toString(CHALLENGE_PRIVATE);
		challengeJsonAdmin = "}, \"admin\":"+ Long.toString(CHALLENGE_ADMIN) + "}]";
		
		challengesIncomingJsonString = "{\"challenges\":["+
				"{\"info\":{\"title\":\""+CHALLENGE_TITLE+"\"," +
				"\"location\":\""+CHALLENGE_LOC+"\"," +
				"\"isprivate\":true}," +
				"\"links\":[{\"rel\":\"" + CHALLENGE_SELF_REL + "\",\"uri\":\"" + CHALLENGE_REL + CHALLENGEGUID + "\",\"type\":\""+ CHALLENGE_TYPE + "\"}," +
							"{\"rel\":\"" + CHALLENGE_REL + CHALLENGEGUID + "\",\"uri\":\"" + CHALLENGE_URI + "\",\"type\":\"" + PARTICIPANT_TYPE + "\"}" +
							"]}," +
				"{\"info\":{\"title\":\""+CHALLENGE2_TITLE+"\"," +
				"\"location\":\""+CHALLENGE2_LOC+"\"," +
				"\"isprivate\":false},"+
				"\"links\":[{\"rel\":\"" + CHALLENGE_SELF_REL + "\",\"uri\":\"" + CHALLENGE_REL + CHALLENGE2GUID+"\",\"type\":\"" + CHALLENGE_TYPE + "\"}," +
							"{\"rel\":\"" + CHALLENGE_REL + CHALLENGE2GUID + "\",\"uri\":\"" + CHALLENGE_URI + "\",\"type\":\"" + PARTICIPANT_TYPE + "\"}" +
							"]}" +
				"]}";
	}
	
	@Test
	public void testFormatChallengeJsonInfo() {
		IncomingChallengeJsonModel model; 
		List<IncomingChallengeJsonModel> models = new ArrayList<IncomingChallengeJsonModel>();
		for (ChallengeInfoJsonModel challengeModel : challenges) {
			model = new IncomingChallengeJsonModel();
			model.setChallengeJsonModel(challengeModel);
			model.setAdminId(CHALLENGE_ADMIN);
			models.add(model);
		}
		String json = ChallengesJsonFormatter.formatChallengeJsonInfo(models);
		
		// order of json objects not guaranteed - check for substrings
		assertTrue(json.contains(challengeJsonPrefix));
		assertTrue(json.contains(challengeJsonTitle));
		assertTrue(json.contains(challengeJsonLoc));
		assertTrue(json.contains(challengeJsonPrivate));
		assertTrue(json.contains(challengeJsonAdmin));
	}

	@Test
	public void testParseChallengeJsonInfo() {
		List<OutgoingChallengeJsonModel> models = ChallengesJsonFormatter.parseChallengeJsonInfo(challengesIncomingJsonString);

		OutgoingChallengeJsonModel model1 = models.get(0);
		// check model info
		assertEquals(CHALLENGE_TITLE, model1.getInfo().getTitle());
		assertEquals(CHALLENGE_LOC, model1.getInfo().getLocation());
		assertEquals(CHALLENGE_PRIVATE, model1.getInfo().getIsprivate());
		// check links
		List<ResourceLink> links = model1.getLinks();
		ResourceLink link1 = links.get(0);
		assertEquals(CHALLENGE_SELF_REL, link1.getRel());
		assertEquals(CHALLENGE_REL + CHALLENGEGUID, link1.getUri());
		assertEquals(CHALLENGE_TYPE, link1.getType());
		ResourceLink link2 = links.get(1);
		assertEquals(CHALLENGE_REL + CHALLENGEGUID, link2.getRel());
		assertEquals(CHALLENGE_URI, link2.getUri());
		assertEquals(PARTICIPANT_TYPE, link2.getType());
		
		OutgoingChallengeJsonModel model2 = models.get(1);
		// check model info
		assertEquals(CHALLENGE2_TITLE, model2.getInfo().getTitle());
		assertEquals(CHALLENGE2_LOC, model2.getInfo().getLocation());
		assertEquals(CHALLENGE2_PRIVATE, model2.getInfo().getIsprivate());
		// check links
		links = model2.getLinks();
		link1 = links.get(0);
		assertEquals(CHALLENGE_SELF_REL, link1.getRel());
		assertEquals(CHALLENGE_REL + CHALLENGE2GUID, link1.getUri());
		assertEquals(CHALLENGE_TYPE, link1.getType());
		link2 = links.get(1);
		assertEquals(CHALLENGE_REL + CHALLENGE2GUID, link2.getRel());
		assertEquals(CHALLENGE_URI, link2.getUri());
		assertEquals(PARTICIPANT_TYPE, link2.getType());
	}
	
	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}

}

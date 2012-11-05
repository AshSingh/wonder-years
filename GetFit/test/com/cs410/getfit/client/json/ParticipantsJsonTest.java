package com.cs410.getfit.client.json;

import java.util.List;

import org.junit.Test;

import com.cs410.getfit.server.challenges.json.OutgoingParticipantJsonModel;
import com.cs410.getfit.server.json.ResourceLink;
import com.google.gwt.junit.client.GWTTestCase;

public class ParticipantsJsonTest extends GWTTestCase {
	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}

	private long challengeId = 2;
	private long userId = 1;
	private boolean isAdmin = true;
	private long userId2 =2;
	private boolean isAdmin2 = false;
	private long partiId = 1;
	private long partiId2 = 2;
	private long dateJoined = System.currentTimeMillis();
	private long dateJoined2 = System.currentTimeMillis() + 1000000;
	
	String incomingJson = "{\"participants\":[{\"info\":" +
												"{\"userId\":"+userId+"," +
												"\"isAdmin\":"+isAdmin+"," +
												"\"dateJoined\":"+dateJoined+"}," +
											"\"links\":[{\"rel\":\"self\"," +
															"\"uri\":\"/challenges/"+challengeId+"/participants/"+partiId+"\"," +
															"\"type\":\"participant\"}," +
														"{\"rel\":\"self\"," +
															"\"uri\":\"/users/"+userId+"\"," +
															"\"type\":\"user\"}" +
														"]}," +
											"{\"info\":" +
												"{\"userId\":"+userId2+"," +
												"\"isAdmin\":"+isAdmin2+"," +
												"\"dateJoined\":"+dateJoined2+"}," +
											"\"links\":[{\"rel\":\"self\"," +
															"\"uri\":\"/challenges/"+challengeId+"/participants/"+partiId2+"\"," +
															"\"type\":\"participant\"}," +
														"{\"rel\":\"self\"," +
															"\"uri\":\"/users/"+userId2+"\"," +
															"\"type\":\"user\"}" +
														"]}" +
											"]" +
							"}";
	
	@Test
	public void testParseChallengeJsonInfo() {
		List<OutgoingParticipantJsonModel> models = ParticipantsJsonFormatter.parseParticipantsJsonInfo(incomingJson);

		OutgoingParticipantJsonModel model1 = models.get(0);
		// check model info
		assertEquals(userId, model1.getInfo().getUserId());
		assertTrue(model1.getInfo().isAdmin());
		assertEquals(dateJoined, model1.getInfo().getDateJoined());
		// check links
		List<ResourceLink> links = model1.getLinks();
		ResourceLink link1 = links.get(0);
		assertEquals("self", link1.getRel());
		assertEquals("/challenges/"+challengeId+"/participants/"+partiId, link1.getUri());
		assertEquals("participant", link1.getType());
		ResourceLink link2 = links.get(1);
		assertEquals("self", link2.getRel());
		assertEquals("/users/"+userId, link2.getUri());
		assertEquals("user", link2.getType());
		
		OutgoingParticipantJsonModel model2 = models.get(1);
		// check model info
		assertEquals(userId2, model2.getInfo().getUserId());
		assertFalse(model2.getInfo().isAdmin());
		assertEquals(dateJoined2, model2.getInfo().getDateJoined());
		// check links
		links = model2.getLinks();
		link1 = links.get(0);
		assertEquals("self", link1.getRel());
		assertEquals("/challenges/"+challengeId+"/participants/"+partiId2, link1.getUri());
		assertEquals("participant", link1.getType());
		link2 = links.get(1);
		assertEquals("self", link2.getRel());
		assertEquals("/users/"+userId2, link2.getUri());
		assertEquals("user", link2.getType());
	}
}


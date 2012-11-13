package com.cs410.getfit.client.json;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cs410.getfit.shared.IncomingParticipantJsonModel;
import com.cs410.getfit.shared.OutgoingParticipantJsonModel;
import com.cs410.getfit.shared.ParticipantInfoJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Cookies;

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
	
	String outgoingJson = "{\"participants\":[{\"info\":" +
				"{\"userId\":"+userId+"," +
				"\"isAdmin\":"+isAdmin+"}" +
			"}," +
			"{\"info\":" +
				"{\"userId\":"+userId2+"," +
				"\"isAdmin\":"+isAdmin2+"}" +
			"}]" +
			"}";
	// strings to verify
	String participantJsonPrefix = "{\"participants\":[{\"info\":{";
	String participantJsonUserId = "userId\":"+userId;
	String participantJsonIsAdmin = "\"isAdmin\":"+isAdmin;
	String participantJsonUserId2 = "userId\":"+userId2;
	String participantJsonIsAdmin2 = "\"isAdmin\":"+isAdmin2;	
	
	@Test
	public void testFormatParticipantsJsonInfo() {
		Cookies.setCookie("accessToken", "r109809f");
		List<IncomingParticipantJsonModel> models = new ArrayList<IncomingParticipantJsonModel>();
		
		IncomingParticipantJsonModel model = new IncomingParticipantJsonModel(); 
		ParticipantInfoJsonModel participantModel = new ParticipantInfoJsonModel();
		participantModel.setAdmin(isAdmin);
		participantModel.setUserId(userId);
		model.setParticipantInfoJsonModel(participantModel);	
		models.add(model);
		
		IncomingParticipantJsonModel model2 = new IncomingParticipantJsonModel(); 
		ParticipantInfoJsonModel participantModel2 = new ParticipantInfoJsonModel();
		participantModel2.setAdmin(isAdmin2);
		participantModel2.setUserId(userId2);
		model2.setParticipantInfoJsonModel(participantModel2);		
		models.add(model2);

		String json = ParticipantsJsonFormatter.formatParticipantsJsonInfo(models);
		
		// order of json objects not guaranteed - check for substrings
		assertTrue(json.contains(participantJsonPrefix));
		assertTrue(json.contains(participantJsonUserId));
		assertTrue(json.contains(participantJsonIsAdmin));
		assertTrue(json.contains(participantJsonUserId2));
		assertTrue(json.contains(participantJsonIsAdmin2));
	}
	
	@Test
	public void testParseParticipantsJsonInfo() {
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


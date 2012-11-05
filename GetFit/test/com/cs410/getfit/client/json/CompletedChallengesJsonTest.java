package com.cs410.getfit.client.json;

import java.util.List;

import org.junit.Test;

import com.cs410.getfit.server.challenges.json.OutgoingCompletedChallengeJsonModel;
import com.cs410.getfit.server.json.ResourceLink;
import com.google.gwt.junit.client.GWTTestCase;

public class CompletedChallengesJsonTest extends GWTTestCase {
	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}
	private long userId = 1;
	private long userId2 =2;
	private long challengeId = 2;
	private long cCh1Id = 1;
	private long cCh2Id = 2;
	private long dateCompleted = System.currentTimeMillis();
	private long dateCompleted2 = System.currentTimeMillis() + 1000000;

	String incomingJson ="{\"completedchallenges\":[{\"info\":{\"userId\":"+userId+",\"dateCompleted\":"+dateCompleted+"}," +
			"\"links\":[{\"rel\":\"self\",\"uri\":\"/challenges/"+challengeId+"/completedchallenges/"+cCh1Id+"\",\"type\":\"completedchallenge\"},{\"rel\":\"self\",\"uri\":\"/users/"+userId+"\",\"type\":\"user\"}]}," +
			"{\"info\":{\"userId\":"+userId2+",\"dateCompleted\":"+dateCompleted2+"}," +
			"\"links\":[{\"rel\":\"self\",\"uri\":\"/challenges/"+challengeId+"/completedchallenges/"+cCh2Id+"\",\"type\":\"completedchallenge\"},{\"rel\":\"self\",\"uri\":\"/users/"+userId2+"\",\"type\":\"user\"}]}]}";

	@Test
	public void testParseCompletedChallengesJsonInfo() {
		List<OutgoingCompletedChallengeJsonModel> models = CompletedChallengesJsonFormatter.parseCompletedChallengesJsonInfo(incomingJson);

		OutgoingCompletedChallengeJsonModel model1 = models.get(0);
		// check model info
		assertEquals(userId, model1.getInfo().getUserId());
		assertEquals(dateCompleted, model1.getInfo().getDateCompleted());
		// check links
		List<ResourceLink> links = model1.getLinks();
		ResourceLink link1 = links.get(0);
		assertEquals("self", link1.getRel());
		assertEquals("/challenges/"+challengeId+"/completedchallenges/"+cCh1Id, link1.getUri());
		assertEquals("completedchallenge", link1.getType());
		ResourceLink link2 = links.get(1);
		assertEquals("self", link2.getRel());
		assertEquals("/users/"+userId, link2.getUri());
		assertEquals("user", link2.getType());
		
		OutgoingCompletedChallengeJsonModel model2 = models.get(1);
		// check model info
		assertEquals(userId2, model2.getInfo().getUserId());
		assertEquals(dateCompleted2, model2.getInfo().getDateCompleted());
		// check links
		links = model2.getLinks();
		link1 = links.get(0);
		assertEquals("self", link1.getRel());
		assertEquals("/challenges/"+challengeId+"/completedchallenges/"+cCh2Id, link1.getUri());
		assertEquals("completedchallenge", link1.getType());
		link2 = links.get(1);
		assertEquals("self", link2.getRel());
		assertEquals("/users/"+userId2, link2.getUri());
		assertEquals("user", link2.getType());
	}
}

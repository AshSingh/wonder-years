package com.cs410.getfit.client.json;

import java.util.List;

import org.junit.Test;

import com.cs410.getfit.shared.OutgoingChallengeHistoryJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.google.gwt.junit.client.GWTTestCase;

public class ChallengeHistoryJsonTest extends GWTTestCase {
	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}

	private long challengeId = 2;
	private long userId = 1;
	private long dateModified = System.currentTimeMillis();
	private String type = "CREATE";

	String incomingJson = "{\"user_newsfeed\":[{\"info\":" +
			"{\"datemodified\":"+dateModified+"," +
			"\"newsfeedItemType\":\""+type+"\"}," +
			"\"links\":[{\"rel\":\"self\"," +
			"\"uri\":\"/users/"+userId+"\"," +
			"\"type\":\"user\"}," +
			"{\"rel\":\"self\"," +
			"\"uri\":\"/challenges/"+challengeId+"\"," +
			"\"type\":\"challenge\"}" +
			"]}" +
			"]" +
			"}";

	@Test
	public void testParseChallengeHistoryJsonInfo() {
		List<OutgoingChallengeHistoryJsonModel> models = ChallengeHistoryJsonFormatter.parseChallengeHistoryJsonInfo(incomingJson);

		OutgoingChallengeHistoryJsonModel model1 = models.get(0);
		// check model info
		assertEquals(dateModified, model1.getInfo().getDatemodified());
		assertEquals(type, model1.getInfo().getNewsfeedItemType());
		// check links
		List<ResourceLink> links = model1.getLinks();
		ResourceLink link1 = links.get(0);
		assertEquals("self", link1.getRel());
		assertEquals("/users/"+userId, link1.getUri());
		assertEquals("user", link1.getType());
		ResourceLink link2 = links.get(1);
		assertEquals("self", link2.getRel());
		assertEquals("/challenges/"+challengeId, link2.getUri());
		assertEquals("challenge", link2.getType());

	}
}
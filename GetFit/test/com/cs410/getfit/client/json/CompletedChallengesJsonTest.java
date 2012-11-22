package com.cs410.getfit.client.json;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cs410.getfit.shared.CompletedChallengeInfoJsonModel;
import com.cs410.getfit.shared.IncomingCompletedChallengeJsonModel;
import com.cs410.getfit.shared.OutgoingCompletedChallengeJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Cookies;

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
	private String accessToken = "r109809f";

	String incomingJson ="{\"completedchallenges\":[{\"info\":{\"userId\":"+userId+",\"dateCompleted\":"+dateCompleted+"}," +
			"\"links\":[{\"rel\":\"self\",\"uri\":\"/challenges/"+challengeId+"/completedchallenges/"+cCh1Id+"\",\"type\":\"completedchallenge\"},{\"rel\":\"self\",\"uri\":\"/users/"+userId+"\",\"type\":\"user\"}]}," +
			"{\"info\":{\"userId\":"+userId2+",\"dateCompleted\":"+dateCompleted2+"}," +
			"\"links\":[{\"rel\":\"self\",\"uri\":\"/challenges/"+challengeId+"/completedchallenges/"+cCh2Id+"\",\"type\":\"completedchallenge\"},{\"rel\":\"self\",\"uri\":\"/users/"+userId2+"\",\"type\":\"user\"}]}]}";

	String outgoingJson = "{\"completedchallenges\":[{\"info\":" +
			"{\"userId\":"+userId+"" +
			 "}}," +
			 "{\"info\":" +
						"{\"userId\":"+userId2+"" +
			 "}}], \"accessToken\":\"" + accessToken + "\"" +
			 "}";

	
	@Test
	public void testFormatCompletedChallengesJsonInfo() {
		Cookies.setCookie("accessToken", accessToken);
		List<IncomingCompletedChallengeJsonModel> models = new ArrayList<IncomingCompletedChallengeJsonModel>();
		
		IncomingCompletedChallengeJsonModel model = new IncomingCompletedChallengeJsonModel(); 
		CompletedChallengeInfoJsonModel challengeModel = new CompletedChallengeInfoJsonModel();
		model.setCompletedChallengeInfoJsonModel(challengeModel);		
		model.setUserId(userId);
		models.add(model);
		
		IncomingCompletedChallengeJsonModel model2 = new IncomingCompletedChallengeJsonModel(); 
		CompletedChallengeInfoJsonModel challengeModel2 = new CompletedChallengeInfoJsonModel();
		model2.setCompletedChallengeInfoJsonModel(challengeModel2);		
		model2.setUserId(userId2);
		models.add(model2);

		String json = CompletedChallengesJsonFormatter.formatCompletedChallengeJsonInfo(models);

		assertEquals(outgoingJson, json);
	}
	
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

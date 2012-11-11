package com.cs410.getfit.client.json;

import java.util.List;

import org.junit.Test;

import com.cs410.getfit.shared.OutgoingUserJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Cookies;

public class UsersJsonTest extends GWTTestCase {
	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}
	
	private final String user1fname = "User1_FirstName";
	private final String user1lname = "User1_LastName";
	private final String user2fname = "User2_FirstName";
	private final String user2lname = "User2_LastName";
	
	private String incomingJson = "{\"users\":[{\"info\":{\"firstname\":\""+user1fname+"\"," +
			"\"lastname\":\""+user1lname+"\"," +
			"\"isPrivate\":true}," +
			"\"links\":[{\"rel\":\"self\",\"uri\":\"/users/1\",\"type\":\"user\"}," +
						"{\"rel\":\"/users/1\",\"uri\":\"/newsfeed\",\"type\":\"newsfeed\"}," +
						"{\"rel\":\"/users/1\",\"uri\":\"/challenges\",\"type\":\"userchallenges\"}]}," +
			"{\"info\":{\"firstname\":\""+user2fname+"\"," +
			"\"lastname\":\""+user2lname+"\"," +
			"\"isPrivate\":false}," +
			"\"links\":[{\"rel\":\"self\",\"uri\":\"/users/2\",\"type\":\"user\"}," +
						"{\"rel\":\"/users/2\",\"uri\":\"/newsfeed\",\"type\":\"newsfeed\"}," +
						"{\"rel\":\"/users/2\",\"uri\":\"/challenges\",\"type\":\"userchallenges\"}]}]}";
	
	@Test
	public void testParseUserJsonInfo() {
		Cookies.setCookie("accessToken", "r109809f");
		
		List<OutgoingUserJsonModel> models = UsersJsonFormatter.parseUserJsonInfo(incomingJson);

		OutgoingUserJsonModel model1 = models.get(0);
		// check model info
		assertEquals(user1fname, model1.getInfo().getFirstname());
		assertTrue(model1.getInfo().getIsPrivate());
		assertEquals(user1lname, model1.getInfo().getLastname());
		// check links
		List<ResourceLink> links = model1.getLinks();
		ResourceLink link1 = links.get(0);
		assertEquals("self", link1.getRel());
		assertEquals("/users/1", link1.getUri());
		assertEquals("user", link1.getType());
		ResourceLink link2 = links.get(1);
		assertEquals("/users/1", link2.getRel());
		assertEquals("/newsfeed", link2.getUri());
		assertEquals("newsfeed", link2.getType());
		ResourceLink link3 = links.get(2);
		assertEquals("/users/1", link3.getRel());
		assertEquals("/challenges", link3.getUri());
		assertEquals("userchallenges", link3.getType());
		
		OutgoingUserJsonModel model2 = models.get(1);
		// check model info
		assertEquals(user2fname, model2.getInfo().getFirstname());
		assertFalse(model2.getInfo().getIsPrivate());
		assertEquals(user2lname, model2.getInfo().getLastname());
		// check links
		links = model2.getLinks();
		link1 = links.get(0);
		assertEquals("self", link1.getRel());
		assertEquals("/users/2", link1.getUri());
		assertEquals("user", link1.getType());
		link2 = links.get(1);
		assertEquals("/users/2", link2.getRel());
		assertEquals("/newsfeed", link2.getUri());
		assertEquals("newsfeed", link2.getType());
		link3 = links.get(2);
		assertEquals("/users/2", link3.getRel());
		assertEquals("/challenges", link3.getUri());
		assertEquals("userchallenges", link3.getType());
	}
}


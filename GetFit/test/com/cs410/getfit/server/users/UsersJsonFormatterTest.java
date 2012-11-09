package com.cs410.getfit.server.users;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cs410.getfit.server.models.User;

@RunWith(JMock.class)
public class UsersJsonFormatterTest {
	private final String fb_id1 = "User1_FB_ID";
	private final String fb_id2 = "User2_FB_ID";
	private final String user1fname = "User1_FirstName";
	private final String user1lname = "User1_LastName";
	private final String user2fname = "User2_FirstName";
	private final String user2lname = "User2_LastName";
	
	private JUnit4Mockery context = new JUnit4Mockery();

	final User user1 = context.mock(User.class, "user1");
	final User user2 = context.mock(User.class, "user2");

	private String usersJsonString = "{\"users\":"
			+ "[{\"FB_ID\":\""+fb_id1+"\",\"firstname\":\""+user1fname+"\",\"lastname\":\""+user1lname+"\",\"isPrivate\":\"true\"},"
			+ "{\"FB_ID\":\""+fb_id2+"\",\"firstname\":\""+user2fname+"\",\"lastname\":\""+user2lname+"\",\"isPrivate\":\"false\"}"
			+ "]}";;
	private String outgoingJsonString = "{\"users\":[{\"info\":{\"firstname\":\""+user1fname+"\"," +
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
	List<User> users;
	UsersJsonFormatter formatter = new UsersJsonFormatter();

	@Before
	public void setUp() {
		users = new ArrayList<User>();
		users.add(user1);
		users.add(user2);
		context.checking(new Expectations() {
			{
				allowing(user1).getFB_ID(); will(returnValue(fb_id1));
				allowing(user1).getGuid(); will(returnValue((long)1));
				allowing(user1).getFirstName(); will(returnValue(user1fname));
				allowing(user1).getLastName(); will(returnValue(user1lname));
				allowing(user1).getIsPrivate(); will(returnValue(true));
				
				allowing(user2).getFB_ID(); will(returnValue(fb_id2));
				allowing(user2).getGuid(); will(returnValue((long)2));
				allowing(user2).getFirstName(); will(returnValue(user2fname));
				allowing(user2).getLastName(); will(returnValue(user2lname));
				allowing(user2).getIsPrivate(); will(returnValue(false));
			}
		});
	}

	@Test
	public void testGetJsonFormattedUserArray() {
		String jsonFormattedUsers = formatter.getJSONFormattedStringOfResource(users);
		assertEquals(outgoingJsonString, jsonFormattedUsers);
	}

	@Test
	public void testGetUsersFromJsonFormattedString() {
		
		List<User> actualUsers = formatter.getResourcesFromJSONFormattedString(usersJsonString);
		assertEquals(2, actualUsers.size());
		User user1 = actualUsers.get(0);
		assertEquals(user1fname, user1.getFirstName());
		assertEquals(user1lname, user1.getLastName());
		assertEquals(fb_id1, user1.getFB_ID());
		assertEquals(true, user1.getIsPrivate());

		User user2 = actualUsers.get(1);
		assertEquals(user2fname, user2.getFirstName());
		assertEquals(user2lname, user2.getLastName());
		assertEquals(fb_id2, user2.getFB_ID());
		assertEquals(false, user2.getIsPrivate());
	}

}

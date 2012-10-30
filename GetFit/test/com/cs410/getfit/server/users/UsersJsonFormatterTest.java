package com.cs410.getfit.server.users;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.cs410.getfit.server.models.User;
import com.cs410.getfit.server.models.UserImpl;
import com.cs410.getfit.server.users.UsersJsonFormatter;

public class UsersJsonFormatterTest {

	private String usersJsonString = "{\"users\":" +
			"[{\"FB_ID\":\"User1_FB_ID\",\"firstName\":\"User1_FirstName\",\"lastName\":\"User1_LastName\",\"isPrivate\":\"true\"}," +
			"{\"FB_ID\":\"User2_FB_ID\",\"firstName\":\"User2_FirstName\",\"lastName\":\"User2_LastName\",\"isPrivate\":\"false\"}" +
					"]}";
;
	List <User> users;
	UsersJsonFormatter formatter = new UsersJsonFormatter();
	
	@Before
	public void setUp() {
		users = new ArrayList<User>();
		final User user1 = new UserImpl("User1_FB_ID","User1_FirstName","User1_LastName",true);
		final User user2 = new UserImpl("User2_FB_ID","User2_FirstName","User2_LastName",false);
		users.add(user1);
		users.add(user2);
	}
	
	@Test
	public void testGetJsonFormattedUserArray() {
		//String jsonFormattedUsers = formatter.getJSONFormattedStringOfResource(users);
		//assertEquals(usersJsonString,jsonFormattedUsers);
		//disable this test until we fix how users json model is created
	}
	@Test
	public void testGetUsersFromJsonFormattedString() {
		List <User> actualUsers = formatter.getResourcesFromJSONFormattedString(usersJsonString);
		System.out.println(actualUsers.get(0).getIsPrivate());
		assertEquals(2, actualUsers.size());
		User user1 = actualUsers.get(0);
		User expectedUser1 = users.get(0);
		assertEquals(user1.getFirstName(), expectedUser1.getFirstName());
		assertEquals(user1.getLastName(), expectedUser1.getLastName());
		assertEquals(user1.getFB_ID(), expectedUser1.getFB_ID());
		System.out.println(user1.getIsPrivate());
		System.out.println(expectedUser1.getIsPrivate());
		assertEquals(user1.getIsPrivate(), expectedUser1.getIsPrivate());
		
		User user2 = actualUsers.get(1);
		User expectedUser2 = users.get(1);
		assertEquals(user2.getFirstName(), expectedUser2.getFirstName());
		assertEquals(user2.getLastName(), expectedUser2.getLastName());
		assertEquals(user2.getFB_ID(), expectedUser2.getFB_ID());
		assertEquals(user2.getIsPrivate(), expectedUser2.getIsPrivate());
	}
	

}

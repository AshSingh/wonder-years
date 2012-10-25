package com.cs410.getfit.server.users;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.cs410.getfit.server.models.User;
import com.cs410.getfit.server.models.UserImpl;

public class UsersJsonFormatterTest {

	private String usersJsonString = "{\"users\":" +
			"[{\"username\":\"User1_Username\",\"password\":\"User1_Password\",\"firstname\":\"User1_FirstName\",\"lastname\":\"User1_LastName\"}," +
			"{\"username\":\"User2_Username\",\"password\":\"User2_Password\",\"firstname\":\"User2_FirstName\",\"lastname\":\"User2_LastName\"}" +
					"]}";
;
	List <User> users;
	UsersJsonFormatter formatter = new UsersJsonFormatter();
	
	@Before
	public void setUp() {
		users = new ArrayList<User>();
		final User user1 = new UserImpl("User1_Username","User1_Password","User1_FirstName","User1_LastName");
		final User user2 = new UserImpl("User2_Username","User2_Password","User2_FirstName","User2_LastName");
		users.add(user1);
		users.add(user2);
	}
	
	@Test
	public void testGetJsonFormattedUserArray() {
		String jsonFormattedUsers = formatter.getJSONFormattedStringOfResource(users);
		assertEquals(usersJsonString,jsonFormattedUsers);
	}
	@Test
	public void testGetUsersFromJsonFormattedString() {
		List <User> actualUsers = formatter.getResourcesFromJSONFormattedString(usersJsonString);
		assertEquals(2, actualUsers.size());
		User user1 = actualUsers.get(0);
		User expectedUser1 = users.get(0);
		assertEquals(user1.getFirstName(), expectedUser1.getFirstName());
		assertEquals(user1.getLastName(), expectedUser1.getLastName());
		assertEquals(user1.getPassword(), expectedUser1.getPassword());
		assertEquals(user1.getUsername(), expectedUser1.getUsername());
		
		User user2 = actualUsers.get(1);
		User expectedUser2 = users.get(1);
		assertEquals(user2.getFirstName(), expectedUser2.getFirstName());
		assertEquals(user2.getLastName(), expectedUser2.getLastName());
		assertEquals(user2.getPassword(), expectedUser2.getPassword());
		assertEquals(user2.getUsername(), expectedUser2.getUsername());
	}
	

}

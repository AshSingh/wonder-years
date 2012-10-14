package com.cs410.getfit.server.users;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cs410.getfit.shared.User;

@RunWith(JMock.class)
public class UsersJsonFormatterTest {

	private Mockery context = new Mockery();
	private String usersJsonString = "{\"users\":["+
			"{\"username\":\"User1_Username\",\"firstname\":\"User1_FirstName\",\"lastname\":\"User1_LastName\",\"password\":\"User1_Password\"},"+
			"{\"username\":\"User2_username\",\"firstname\":\"User2_Firstname\",\"lastname\":\"User2_Lastname\",\"password\":\"User2_password\"}"+
			"]}";
	List <User> users;
	
	@Before
	public void setUp() {
		users = new ArrayList<User>();
		final User user1 = context.mock(User.class, "user1");
		final User user2 = context.mock(User.class, "user2");
		context.checking(new Expectations() {
			{
				allowing(user1).getFirstName(); will(returnValue("User1_FirstName"));
				allowing(user1).getLastName(); will(returnValue("User1_LastName"));
				allowing(user1).getUsername(); will(returnValue("User1_Username"));
				allowing(user1).getPassword(); will(returnValue("User1_Password"));

				allowing(user2).getFirstName(); will(returnValue("User2_Firstname"));
				allowing(user2).getLastName(); will(returnValue("User2_Lastname"));
				allowing(user2).getPassword(); will(returnValue("User2_password"));
				allowing(user2).getUsername(); will(returnValue("User2_username"));
			}
		});
		users.add(user1);
		users.add(user2);
	}
	
	@Test
	public void testGetJsonFormattedUserArray() {
		String jsonFormattedUsers = UsersJsonFormatter.getJSONFormattedStringOfUsers(users);
		assertEquals(usersJsonString,jsonFormattedUsers);
	}
	@Test
	public void testGetUsersFromJsonFormattedString() {
		List <User> actualUsers = UsersJsonFormatter.getUsersFromJSONFormattedString(usersJsonString);
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

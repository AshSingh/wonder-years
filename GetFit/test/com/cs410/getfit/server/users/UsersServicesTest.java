package com.cs410.getfit.server.users;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import com.cs410.getfit.server.models.User;
import com.cs410.getfit.server.users.services.UsersServicesImpl;
import com.j256.ormlite.dao.Dao;

public class UsersServicesTest {
	JUnit4Mockery context = new JUnit4Mockery();

	Dao<User, String> userDao = context.mock(Dao.class,"userDao");

	UsersServicesImpl services = new UsersServicesImpl();
	
	@Before
	public void setUp(){
	services.setUserDao(userDao);
	}
	
	@Test
	public void testQueryForAllUsers() throws SQLException {
		context.checking(new Expectations() {
			{
				oneOf(userDao).queryForAll();
			}
		});
		services.queryForAllUsers();
	}
	@Test
	public void testQueryForAllUsersSQLException() {
		try {
			context.checking(new Expectations() {
				{
					oneOf(userDao).queryForAll();will(throwException(new SQLException()));
				}
			});
			assert(false);
		} catch (SQLException e) {
			assert(true);
		}
		services.queryForAllUsers();
	}
	
	@Test
	public void testCreateUser() throws SQLException {
		final User user = context.mock(User.class);
		context.checking(new Expectations() {
			{
				allowing(user).getFB_ID(); will(returnValue("1"));
				allowing(userDao).create(user);
			}
		});
		services.createUser(user);
	}
	
	@Test
	public void testGetUserByFB_ID() throws SQLException {
		final User user = context.mock(User.class);
		final ArrayList<User> users = new ArrayList<User>();
		users.add(user);
		final String fb_id= "fb_id";
		context.checking(new Expectations() {
			{
				allowing(user).getFB_ID(); will(returnValue(1));
				allowing(userDao).queryForEq("FB_ID", fb_id); will(returnValue(users));
			}
		});
		
		assertEquals("should return correct user", user, services.getUser(fb_id));
	}

	@Test
	public void testGetUserById() throws SQLException {
		final User user = context.mock(User.class);
		final String guid = "2";
		context.checking(new Expectations() {
			{
				allowing(user).getFB_ID(); will(returnValue(1));
				allowing(userDao).queryForId(guid); will(returnValue(user));
			}
		});
		
		assertEquals("should return correct user", user, services.getUserById(guid));
	}
}

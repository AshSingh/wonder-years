package com.cs410.getfit.server.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserUriParserTest {

	private String usersPattern = "/users";
	private String usersIdPattern = "/users/2";
	private String newsfeedPattern = "/users/10/newsfeed";
	private String challengesPattern = "/users/4/challenges";
	private String loginPattern = "/login";
	private String invalidUri = "/invalidUri";
	private int defaultId = -1;
	
	@Test
	public void testUsersPattern() {
		UserUriParser parser = new UserUriParser(usersPattern);
		assertEquals(UserUriParser.USERS, parser.getResource());
		assertTrue(parser.isUsersUri());
		assertFalse(parser.isChallengesUri());
		assertFalse(parser.isNewsfeedUri());
		assertEquals(defaultId, parser.getUserId());
	}
	@Test
	public void testUserIdPattern() {
		UserUriParser parser = new UserUriParser(usersIdPattern);
		assertEquals(UserUriParser.USERSID, parser.getResource());
		assertTrue(parser.isUsersUri());
		assertFalse(parser.isChallengesUri());
		assertFalse(parser.isNewsfeedUri());
		assertEquals(2, parser.getUserId());
	}
	@Test
	public void testNewsfeedPattern() {
		UserUriParser parser = new UserUriParser(newsfeedPattern);
		assertEquals(UserUriParser.NEWSFEED, parser.getResource());
		assertFalse(parser.isUsersUri());
		assertFalse(parser.isChallengesUri());
		assertTrue(parser.isNewsfeedUri());
		assertEquals(10, parser.getUserId());
	}
	@Test
	public void testChallengesPattern() {
		UserUriParser parser = new UserUriParser(challengesPattern);
		assertEquals(UserUriParser.CHALLENGES, parser.getResource());
		assertFalse(parser.isUsersUri());
		assertTrue(parser.isChallengesUri());
		assertFalse(parser.isNewsfeedUri());
		assertEquals(4, parser.getUserId());
	}
	@Test
	public void testLoginPattern() {
		UserUriParser parser = new UserUriParser(loginPattern);
		assertEquals(UserUriParser.LOGIN, parser.getResource());
		assertFalse(parser.isUsersUri());
		assertFalse(parser.isChallengesUri());
		assertFalse(parser.isNewsfeedUri());
		assertEquals(defaultId, parser.getUserId());
	}
	
	@Test
	public void testInvalidPattern() {
		UserUriParser parser = new UserUriParser(invalidUri);
		assertEquals(UserUriParser.INVALID_URI, parser.getResource());
		assertFalse(parser.isUsersUri());
		assertFalse(parser.isChallengesUri());
		assertFalse(parser.isNewsfeedUri());
		assertEquals(defaultId, parser.getUserId());
	}
}

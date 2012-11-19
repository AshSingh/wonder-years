package com.cs410.getfit.server.users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * An object whose function is to parse URIs coming into the UsersServlet
 */
public class UserUriParser {
	public static final int INVALID_URI = -1;
	public static final int USERS = 0;
	public static final int USERSID = 1;
	public static final int NEWSFEED = 2;
	public static final int CHALLENGES = 3;
	public static final int LOGIN = 4;
	
	private int resource = -1;

	private static final Pattern usersPattern = Pattern.compile(".*/users");
	private static final Pattern usersIdPattern = Pattern
			.compile(".*/users/([0-9]*)");
	private static final Pattern newsfeedPattern = Pattern
			.compile(".*/users/([0-9]*)/newsfeed");
	private static final Pattern challengesPattern = Pattern
			.compile(".*/users/([0-9]*)/challenges");
	Pattern loginPattern = Pattern
			.compile(".*/login");
	private long userId = -1;

	public UserUriParser(String pathURI) {
		setResource(pathURI);
	}

	public void setResource(String pathURI) {
		Matcher matcher;
		matcher = loginPattern.matcher(pathURI);
		if (matcher.find()) {
			resource = LOGIN;
			return;
		}
		matcher = newsfeedPattern.matcher(pathURI);
		if (matcher.find()) {
			userId = Long.parseLong(matcher.group(1));
			resource = NEWSFEED;
			return;
		}
		matcher = challengesPattern.matcher(pathURI);
		if (matcher.find()) {
			userId = Long.parseLong(matcher.group(1));
			resource = CHALLENGES;
			return;
		}

		matcher = usersIdPattern.matcher(pathURI);
		if (matcher.find()) {
			userId = Long.parseLong((matcher.group(1)));
			resource = USERSID;
			return;
		}

		matcher = usersPattern.matcher(pathURI);
		if (matcher.find()) {
			resource = USERS;
			return;
		}
		resource = INVALID_URI;
	}
	/**
	 * 
	 * @return user id in uri, -1 if none defined
	 */
	public long getUserId() {
		return userId;
	}
	/**
	 * 
	 * @return resource of uri
	 */
	public int getResource() {
		return resource;
	}
	/**
	 * 
	 * @return true if resource is /users(/id), false otherwise
	 */
	public boolean isUsersUri() {
		return resource == UserUriParser.USERS
				|| resource == UserUriParser.USERSID;
	}
	/**
	 * 
	 * @return true if resource is /users/id/newsfeed(/id), false otherwise
	 */
	public boolean isNewsfeedUri() {
		return resource == UserUriParser.NEWSFEED;
	}
	/**
	 * 
	 * @return true if resource is /users/id/challenges(/id), false otherwise
	 */
	public boolean isChallengesUri() {
		return resource == UserUriParser.CHALLENGES;
	}
}

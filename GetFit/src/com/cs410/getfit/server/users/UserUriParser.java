package com.cs410.getfit.server.users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUriParser {
	public static final int INVALID_URI = -1;
	public static final int USERS = 0;
	public static final int USERSID = 1;
	public static final int NEWSFEED = 2;
	public static final int CHALLENGES = 3;
	
	private int resource = -1;

	private static final Pattern usersPattern = Pattern.compile("/users");
	private static final Pattern usersIdPattern = Pattern
			.compile("/users/([0-9]*)");
	private static final Pattern newsfeedPattern = Pattern
			.compile("/users/([0-9]*)/newsfeed");
	private static final Pattern challengesPattern = Pattern
			.compile("/users/([0-9]*)/challenges");
	private long userId = -1;

	public UserUriParser(String pathURI) {
		setResource(pathURI);
	}

	public void setResource(String pathURI) {
		Matcher matcher;
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

	public long getUserId() {
		return userId;
	}

	public int getResource() {
		return resource;
	}

	public boolean isUsersUri() {
		return resource == UserUriParser.USERS
				|| resource == UserUriParser.USERSID;
	}

	public boolean isNewsfeedUri() {
		return resource == UserUriParser.NEWSFEED;
	}
	
	public boolean isChallengesUri() {
		return resource == UserUriParser.CHALLENGES;
	}
}

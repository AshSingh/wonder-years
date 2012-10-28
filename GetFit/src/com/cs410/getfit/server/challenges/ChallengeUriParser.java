package com.cs410.getfit.server.challenges;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChallengeUriParser {
	public static final int INVALID_URI = -1;
	public static final int CHALLENGES = 0;
	public static final int CHALLENGESID = 1;
	public static final int PARTICIPANTS = 2;
	public static final int PARTICIPANTSID = 3;

	private int resource = -1;

	private static final Pattern challengesPattern = Pattern
			.compile("/challenges");
	private static final Pattern challengesIdPattern = Pattern
			.compile("/challenges/([0-9]*)");
	private static final Pattern participantsPattern = Pattern
			.compile("/challenges/([0-9]*)/participants");
	private static final Pattern participantsIdPattern = Pattern
			.compile("/challenges/([0-9]*)/participants/([0-9]*)");
	private long challengeId = -1;
	private long participantId = -1;

	public ChallengeUriParser(String pathURI) {
		setResource(pathURI);
	}

	public void setResource(String pathURI) {
		Matcher matcher;
		matcher = participantsIdPattern.matcher(pathURI);
		if (matcher.find()) {
			challengeId = Long.parseLong(matcher.group(1));
			participantId = Long.parseLong(matcher.group(2));
			resource = PARTICIPANTSID;
			return;
		}
		matcher = participantsPattern.matcher(pathURI);
		if (matcher.find()) {
			challengeId = Long.parseLong(matcher.group(1));
			resource = PARTICIPANTS;
			return;
		}

		matcher = challengesIdPattern.matcher(pathURI);
		if (matcher.find()) {
			challengeId = Long.parseLong((matcher.group(1)));
			resource = CHALLENGESID;
			return;
		}

		matcher = challengesPattern.matcher(pathURI);
		if (matcher.find()) {
			resource = CHALLENGES;
			return;
		} 
		resource = INVALID_URI;
	}

	public long getChallengeId() {
		return challengeId;
	}

	public long getParticipantId() {
		return participantId;
	}

	public int getResource() {
		return resource;
	}

	public boolean isChallengeURI() {
		return resource == ChallengeUriParser.CHALLENGES
				|| resource == ChallengeUriParser.CHALLENGESID;
	}

	public boolean isParticipantURI() {
		return resource == ChallengeUriParser.PARTICIPANTS
				|| resource == ChallengeUriParser.PARTICIPANTSID;
	}
}

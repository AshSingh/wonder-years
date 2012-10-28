package com.cs410.getfit.server.challenges;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ChallengeUriParserTest {

	private String challengesPattern = "/challenges";
	private String challengesIdPattern = "/challenges/9";
	private String participantsPattern = "/challenges/10/participants";
	private String participantsIdPattern = "/challenges/1/participants/2";
	private String invalidUri = "invalidUri";
	private long defaultId = -1;
	
	@Test
	public void testChallengesPattern() {
		ChallengeUriParser parser = new ChallengeUriParser(challengesPattern);
		assertEquals(ChallengeUriParser.CHALLENGES, parser.getResource());
		assertTrue(parser.isChallengeURI());
		assertFalse(parser.isParticipantURI());
		assertEquals(defaultId, parser.getChallengeId());
		assertEquals(defaultId, parser.getParticipantId());
	}
	@Test
	public void testChallengesIdPattern() {
		ChallengeUriParser parser = new ChallengeUriParser(challengesIdPattern);
		assertEquals(ChallengeUriParser.CHALLENGESID, parser.getResource());
		assertTrue(parser.isChallengeURI());
		assertFalse(parser.isParticipantURI());
		assertEquals(9, parser.getChallengeId());
		assertEquals(defaultId, parser.getParticipantId());
	}
	@Test
	public void testParticipantsPattern() {
		ChallengeUriParser parser = new ChallengeUriParser(participantsPattern);
		assertEquals(ChallengeUriParser.PARTICIPANTS, parser.getResource());
		assertFalse(parser.isChallengeURI());
		assertTrue(parser.isParticipantURI());
		assertEquals(10, parser.getChallengeId());
		assertEquals(defaultId, parser.getParticipantId());
	}
	@Test
	public void testParticipantsIdPattern() {
		ChallengeUriParser parser = new ChallengeUriParser(participantsIdPattern);
		assertEquals(ChallengeUriParser.PARTICIPANTSID, parser.getResource());
		assertFalse(parser.isChallengeURI());
		assertTrue(parser.isParticipantURI());
		assertEquals(1, parser.getChallengeId());
		assertEquals(2, parser.getParticipantId());
	}
	@Test
	public void testInvalidPattern() {
		ChallengeUriParser parser = new ChallengeUriParser(invalidUri);
		assertEquals(ChallengeUriParser.INVALID_URI, parser.getResource());
		assertFalse(parser.isChallengeURI());
		assertFalse(parser.isParticipantURI());
		assertEquals(defaultId, parser.getChallengeId());
		assertEquals(defaultId, parser.getParticipantId());
	}
}

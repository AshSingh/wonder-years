package com.cs410.getfit.server.challenges.json;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.CompletedChallenge;
import com.cs410.getfit.server.models.User;

@RunWith(JMock.class)
public class CompletedChallengesJsonFormatterTest {
	private long userId = 1;
	private long userId2 =2;
	private long challengeId = 2;
	private long cCh1Id = 1;
	private long cCh2Id = 2;
	private long dateCompleted = System.currentTimeMillis();
	private JUnit4Mockery context = new JUnit4Mockery();
	private CompletedChallengesJsonFormatter formatter = new CompletedChallengesJsonFormatter();
	String incomingJson = "{\"completedchallenges\":[{\"info\":" +
											"{\"userId\":"+userId+"" +
											 "}}," +
											 "{\"info\":" +
														"{\"userId\":"+userId2+"" +
											 "}}]" +
							"}";
	String outgoingJson ="{\"completedchallenges\":[{\"info\":{\"userId\":"+userId+",\"dateCompleted\":"+dateCompleted+"}," +
													"\"links\":[{\"rel\":\"self\",\"uri\":\"/challenges/"+challengeId+"/completedchallenges/"+cCh1Id+"\",\"type\":\"completedchallenge\"},{\"rel\":\"self\",\"uri\":\"/users/"+userId+"\",\"type\":\"user\"}]}," +
													"{\"info\":{\"userId\":"+userId2+",\"dateCompleted\":"+dateCompleted+"}," +
													"\"links\":[{\"rel\":\"self\",\"uri\":\"/challenges/"+challengeId+"/completedchallenges/"+cCh2Id+"\",\"type\":\"completedchallenge\"},{\"rel\":\"self\",\"uri\":\"/users/"+userId2+"\",\"type\":\"user\"}]}]}";
	@Test
	public void testGetCompletedChallengesFromIncomingJson() {
		List<CompletedChallenge> c_challenges = formatter.getResourcesFromJSONFormattedString(incomingJson);
		assertEquals(2, c_challenges.size());
		CompletedChallenge actual_c_challenge = c_challenges.get(0);
		assertEquals(userId, actual_c_challenge.getUser().getGuid().longValue());
		
		CompletedChallenge actual_c_challenge2 = c_challenges.get(1);
		assertEquals(userId2, actual_c_challenge2.getUser().getGuid().longValue());
	}
	@Test
	public void testGetStringFromOutgoingJson() {
		final CompletedChallenge cCh1 = context.mock(CompletedChallenge.class, "ch1");
		final CompletedChallenge cCh2 = context.mock(CompletedChallenge.class, "ch2");
		
		final User user1 = context.mock(User.class, "user1");
		final User user2 = context.mock(User.class, "user2");
		
		final Challenge ch = context.mock(Challenge.class);
		
		context.checking(new Expectations() {
			{
				allowing(ch).getGuid(); will(returnValue(challengeId));
				
				allowing(cCh1).getUser(); will(returnValue(user1));
				allowing(cCh1).getDateCompleted(); will(returnValue(dateCompleted));
				allowing(cCh1).getChallenge();will(returnValue(ch));
				allowing(cCh1).getGuid();will(returnValue(cCh1Id));
				allowing(user1).getGuid(); will(returnValue(userId));
				
				allowing(cCh2).getUser(); will(returnValue(user2));
				allowing(cCh2).getDateCompleted(); will(returnValue(dateCompleted));
				allowing(cCh2).getChallenge();will(returnValue(ch));
				allowing(cCh2).getGuid();will(returnValue(cCh2Id));
				allowing(user2).getGuid(); will(returnValue(userId2));
			}
		});
		List<CompletedChallenge> c_challenges = new ArrayList<CompletedChallenge>();
		c_challenges.add(cCh1);
		c_challenges.add(cCh2);
		
		String jsonModelString = formatter.getJSONFormattedStringOfResource(c_challenges);
		assertEquals(outgoingJson, jsonModelString);
	}
}

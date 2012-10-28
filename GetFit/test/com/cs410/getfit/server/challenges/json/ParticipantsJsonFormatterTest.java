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
import com.cs410.getfit.server.models.ChallengeUser;
import com.cs410.getfit.server.models.User;

@RunWith(JMock.class)
public class ParticipantsJsonFormatterTest {
	private long challengeId = 2;
	private ParticipantJsonFormatter formatter = new ParticipantJsonFormatter();
	private JUnit4Mockery context = new JUnit4Mockery();
	private long userId = 1;
	private boolean isAdmin = true;
	private long userId2 =2;
	private boolean isAdmin2 = false;
	private long partiId = 1;
	private long partiId2 = 2;
	private long dateJoined = System.currentTimeMillis();
	
	String incomingJson = "{\"participants\":[{\"info\":" +
														"{\"userId\":"+userId+"," +
														"\"isAdmin\":"+isAdmin+"}" +
											 "}," +
											 "{\"info\":" +
														"{\"userId\":"+userId2+"," +
														"\"isAdmin\":"+isAdmin2+"}" +
											 "}]" +
						   "}";
	String outgoingJson = "{\"participants\":[{\"info\":" +
												"{\"userId\":"+userId+"," +
												"\"isAdmin\":"+isAdmin+"," +
												"\"dateJoined\":"+dateJoined+"}," +
											"\"links\":[{\"rel\":\"self\"," +
															"\"uri\":\"/challenges/"+challengeId+"/participants/"+partiId+"\"," +
															"\"type\":\"participant\"}," +
														"{\"rel\":\"self\"," +
															"\"uri\":\"/users/"+userId+"\"," +
															"\"type\":\"user\"}" +
														"]}," +
											"{\"info\":" +
												"{\"userId\":"+userId2+"," +
												"\"isAdmin\":"+isAdmin2+"," +
												"\"dateJoined\":"+dateJoined+"}," +
											"\"links\":[{\"rel\":\"self\"," +
															"\"uri\":\"/challenges/"+challengeId+"/participants/"+partiId2+"\"," +
															"\"type\":\"participant\"}," +
														"{\"rel\":\"self\"," +
															"\"uri\":\"/users/"+userId2+"\"," +
															"\"type\":\"user\"}" +
														"]}" +
											"]" +
							"}";
	
	@Test
	public void testGetParticipantsFromIncomingJson() {
		List<ChallengeUser> participants = formatter.getResourcesFromJSONFormattedString(incomingJson);
		assertEquals(2, participants.size());
		ChallengeUser actualParticipant = participants.get(0);
		assertEquals(userId, actualParticipant.getUser().getGuid().longValue());
		assertEquals(isAdmin, actualParticipant.isAdmin());
		
		ChallengeUser actualParticipant2 = participants.get(1);
		assertEquals(userId2, actualParticipant2.getUser().getGuid().longValue());
		assertEquals(isAdmin2, actualParticipant2.isAdmin());
	}
	@Test
	public void testGetStringFromOutgoingJson() {
		final ChallengeUser parti = context.mock(ChallengeUser.class, "parti1");
		final ChallengeUser parti2 = context.mock(ChallengeUser.class, "parti2");
		
		final User user1 = context.mock(User.class, "user1");
		final User user2 = context.mock(User.class, "user2");
		
		final Challenge ch = context.mock(Challenge.class);
		
		context.checking(new Expectations() {
			{
				allowing(ch).getGuid(); will(returnValue(challengeId));
				
				allowing(parti).getUser(); will(returnValue(user1));
				allowing(parti).getDateJoined(); will(returnValue(dateJoined));
				allowing(parti).getChallenge();will(returnValue(ch));
				allowing(parti).getGuid(); will(returnValue(partiId));
				allowing(parti).isAdmin(); will(returnValue(isAdmin));
				allowing(user1).getGuid(); will(returnValue(userId));
				
				allowing(parti2).getUser(); will(returnValue(user2));
				allowing(parti2).getDateJoined(); will(returnValue(dateJoined));
				allowing(parti2).getChallenge();will(returnValue(ch));
				allowing(parti2).getGuid(); will(returnValue(partiId2));
				allowing(parti2).isAdmin(); will(returnValue(isAdmin2));
				allowing(user2).getGuid(); will(returnValue(userId2));

			}
		});
		List<ChallengeUser> participants = new ArrayList<ChallengeUser>();
		participants.add(parti);
		participants.add(parti2);
		
		String jsonModelString = formatter.getJSONFormattedStringOfResource(participants);
		assertEquals(outgoingJson, jsonModelString);
		
	}

}

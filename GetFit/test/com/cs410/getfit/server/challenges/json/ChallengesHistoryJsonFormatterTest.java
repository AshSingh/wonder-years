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
import com.cs410.getfit.server.models.ChallengeHistory;
import com.cs410.getfit.server.models.User;

@RunWith(JMock.class)
public class ChallengesHistoryJsonFormatterTest {
	private long challengeId = 2;
	private ChallengeHistoryJsonFormatter formatter = new ChallengeHistoryJsonFormatter();
	private JUnit4Mockery context = new JUnit4Mockery();
	private long userId = 1;
	private long dateModified = System.currentTimeMillis();
	private String type = "CREATE";
	private long newsFeedId = 1;

	String outgoingJson = "{\"user_newsfeed\":[{\"info\":" +
												"{\"datemodified\":"+dateModified+"," +
												"\"newsfeedItemType\":\""+type+"\"}," +
											"\"links\":[{\"rel\":\"self\"," +
															"\"uri\":\"/users/"+userId+"/newsfeed/"+newsFeedId+"\"," +
															"\"type\":\"newsfeeditem\"}," +
														"{\"rel\":\"self\"," +
															"\"uri\":\"/users/"+userId+"\"," +
															"\"type\":\"user\"}," +
															"{\"rel\":\"self\"," +
															"\"uri\":\"/challenges/"+challengeId+"\"," +
															"\"type\":\"challenge\"}" +
														"]}" +
											"]" +
							"}";
	
	@Test
	public void testGetStringFromOutgoingJson() {
		final ChallengeHistory chHist = context.mock(ChallengeHistory.class, "chHist");
		
		final User user1 = context.mock(User.class, "user1");
		
		final Challenge ch = context.mock(Challenge.class, "challenge");
		
		context.checking(new Expectations() {
			{
				allowing(ch).getGuid(); will(returnValue(challengeId));
				
				allowing(chHist).getUser(); will(returnValue(user1));
				allowing(chHist).getDateModified(); will(returnValue(dateModified));
				allowing(chHist).getChallenge();will(returnValue(ch));
				allowing(chHist).getGuid(); will(returnValue(newsFeedId));
				allowing(chHist).getHistoryDescription(); will(returnValue(type));
				allowing(user1).getGuid(); will(returnValue(userId));

			}
		});
		List<ChallengeHistory> newsfeed = new ArrayList<ChallengeHistory>();
		newsfeed.add(chHist);
		
		String jsonModelString = formatter.getJSONFormattedStringOfResource(newsfeed);
		assertEquals(outgoingJson, jsonModelString);
		
	}

	@Test
	public void testNotSupportedMethod() {
		assertEquals(null, formatter.getResourcesFromJSONFormattedString(""));
	}
}

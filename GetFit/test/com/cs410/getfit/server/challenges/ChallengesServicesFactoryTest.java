package com.cs410.getfit.server.challenges;

import static org.junit.Assert.assertEquals;

import javax.servlet.ServletException;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.context.WebApplicationContext;

import com.cs410.getfit.server.challenges.services.CompletedChallengeIdServices;
import com.cs410.getfit.server.challenges.services.CompletedChallengeResourceServices;
import com.cs410.getfit.server.challenges.services.CompletedChallengesServices;
import com.cs410.getfit.server.challenges.services.CompletedChallengesServicesFactory;

@RunWith(JMock.class)
public class ChallengesServicesFactoryTest {
	JUnit4Mockery context = new JUnit4Mockery();
	WebApplicationContext ctx = context.mock(WebApplicationContext.class);
	long challengeId = 2;
	long completedChallengeId = 3;
	final CompletedChallengesServices c_challengesServices = new CompletedChallengesServices();
	final CompletedChallengeIdServices c_challengeIdServices = new CompletedChallengeIdServices();
	
	@Test
	public void testCompletedGetChallengesServices() throws ServletException {
		CompletedChallengesServicesFactory factory = new CompletedChallengesServicesFactory(ctx,ChallengeUriParser.COMPLETEDCHALLENGES, challengeId, completedChallengeId);
		context.checking(new Expectations () {
			{
				oneOf(ctx).getBean("completedChallengesService"); will(returnValue(c_challengesServices));
			}
		});
		CompletedChallengeResourceServices services = factory.getCompletedChallengeServices();
		assertEquals("should get back correct services", c_challengesServices, services);
	}
	@Test
	public void testCompletedGetChallengesServicesWithChallenges() throws ServletException {
		CompletedChallengesServicesFactory factory = new CompletedChallengesServicesFactory(ctx,ChallengeUriParser.COMPLETEDCHALLENGES, challengeId, completedChallengeId);
		context.checking(new Expectations () {
			{
				oneOf(ctx).getBean("completedChallengesService"); will(returnValue(c_challengesServices));
			}
		});
		CompletedChallengeResourceServices services = factory.getCompletedChallengeServices(null);
		assertEquals("should get back correct services", c_challengesServices, services);
	}
	@Test
	public void testGetCompletedChallengeIdServices() throws ServletException {
		CompletedChallengesServicesFactory factory = new CompletedChallengesServicesFactory(ctx,ChallengeUriParser.COMEPLETEDCHALLENGESSID, challengeId, completedChallengeId);
		context.checking(new Expectations () {
			{
				oneOf(ctx).getBean("completedChallengesIdService"); will(returnValue(c_challengeIdServices));
			}
		});
		CompletedChallengeResourceServices services = factory.getCompletedChallengeServices(null);
		assertEquals("should get back correct services", c_challengeIdServices, services);
	}
	@Test
	public void testGetCompletedChallengeIdServicesWithChallenges() throws ServletException {
		CompletedChallengesServicesFactory factory = new CompletedChallengesServicesFactory(ctx,ChallengeUriParser.COMEPLETEDCHALLENGESSID, challengeId, completedChallengeId);
		context.checking(new Expectations () {
			{
				oneOf(ctx).getBean("completedChallengesIdService"); will(returnValue(c_challengeIdServices));
			}
		});
		CompletedChallengeResourceServices services = factory.getCompletedChallengeServices();
		assertEquals("should get back correct services", c_challengeIdServices, services);
	}
}

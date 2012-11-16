package com.cs410.getfit.server.challenges;

import static org.junit.Assert.assertEquals;

import javax.servlet.ServletException;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.context.WebApplicationContext;

import com.cs410.getfit.server.challenges.services.ChallengeIdServices;
import com.cs410.getfit.server.challenges.services.ChallengeResourceServices;
import com.cs410.getfit.server.challenges.services.ChallengesServices;
import com.cs410.getfit.server.challenges.services.ChallengesServicesFactory;

@RunWith(JMock.class)
public class CompletedChallengesServicesFactoryTest {
	JUnit4Mockery context = new JUnit4Mockery();
	WebApplicationContext ctx = context.mock(WebApplicationContext.class);
	long challengeId = 2;
	final ChallengesServices challengesServices = new ChallengesServices();
	final ChallengeIdServices challengeIdServices = new ChallengeIdServices();
	
	@Before
	public void setUp(){
	}
	
	@Test
	public void testGetChallengesServices() throws ServletException {
		ChallengesServicesFactory factory = new ChallengesServicesFactory(ctx, ChallengeUriParser.CHALLENGES, challengeId);
		context.checking(new Expectations () {
			{
				oneOf(ctx).getBean("challengesService"); will(returnValue(challengesServices));
			}
		});
		ChallengeResourceServices services = factory.getChallengeServices();
		assertEquals("should get back correct services", challengesServices, services);
	}
	@Test
	public void testGetChallengesServicesWithChallenges() throws ServletException {
		ChallengesServicesFactory factory = new ChallengesServicesFactory(ctx, ChallengeUriParser.CHALLENGES, challengeId);
		context.checking(new Expectations () {
			{
				oneOf(ctx).getBean("challengesService"); will(returnValue(challengesServices));
			}
		});
		ChallengeResourceServices services = factory.getChallengeServices(null);
		assertEquals("should get back correct services", challengesServices, services);
	}
	@Test
	public void testGetChallengeIdServices() throws ServletException {
		ChallengesServicesFactory factory = new ChallengesServicesFactory(ctx, ChallengeUriParser.CHALLENGESID, challengeId);
		context.checking(new Expectations () {
			{
				oneOf(ctx).getBean("challengeService"); will(returnValue(challengeIdServices));
			}
		});
		ChallengeResourceServices services = factory.getChallengeServices();
		assertEquals("should get back correct services", challengeIdServices, services);
	}
	@Test
	public void testGetChallengeIdServicesWithChallenges() throws ServletException {
		ChallengesServicesFactory factory = new ChallengesServicesFactory(ctx, ChallengeUriParser.CHALLENGESID, challengeId);
		context.checking(new Expectations () {
			{
				oneOf(ctx).getBean("challengeService"); will(returnValue(challengeIdServices));
			}
		});
		ChallengeResourceServices services = factory.getChallengeServices(null);
		assertEquals("should get back correct services", challengeIdServices, services);
	}
}

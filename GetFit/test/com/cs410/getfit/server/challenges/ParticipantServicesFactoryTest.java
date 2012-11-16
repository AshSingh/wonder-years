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

import com.cs410.getfit.server.challenges.services.ParticipantIdServices;
import com.cs410.getfit.server.challenges.services.ParticipantResourceServices;
import com.cs410.getfit.server.challenges.services.ParticipantServicesFactory;
import com.cs410.getfit.server.challenges.services.ParticipantsServices;

@RunWith(JMock.class)
public class ParticipantServicesFactoryTest {
	JUnit4Mockery context = new JUnit4Mockery();
	WebApplicationContext ctx = context.mock(WebApplicationContext.class);
	long challengeId = 2;
	long participantId =3;
	final ParticipantsServices participantsServices = new ParticipantsServices();
	final ParticipantIdServices participantsIdServices = new ParticipantIdServices();
	
	@Before
	public void setUp(){
	}
	
	@Test
	public void testGetChallengesServices() throws ServletException {
		ParticipantServicesFactory factory = new ParticipantServicesFactory(ctx, ChallengeUriParser.PARTICIPANTS, challengeId, participantId);
		context.checking(new Expectations () {
			{
				oneOf(ctx).getBean("participantsServices"); will(returnValue(participantsServices));
			}
		});
		ParticipantResourceServices services = factory.getPaticipantServices();
		assertEquals("should get back correct services", participantsServices, services);
	}
	@Test
	public void testGetChallengesServicesWithChallenges() throws ServletException {
		ParticipantServicesFactory factory = new ParticipantServicesFactory(ctx, ChallengeUriParser.PARTICIPANTS, challengeId, participantId);
		context.checking(new Expectations () {
			{
				oneOf(ctx).getBean("participantsServices"); will(returnValue(participantsServices));
			}
		});
		ParticipantResourceServices services = factory.getParticipantServices(null);
		assertEquals("should get back correct services", participantsServices, services);
	}
	@Test
	public void testGetChallengeIdServices() throws ServletException {
		ParticipantServicesFactory factory = new ParticipantServicesFactory(ctx, ChallengeUriParser.PARTICIPANTSID, challengeId, participantId);
		context.checking(new Expectations () {
			{
				oneOf(ctx).getBean("participantIdServices"); will(returnValue(participantsIdServices));
			}
		});
		ParticipantResourceServices services = factory.getPaticipantServices();
		assertEquals("should get back correct services", participantsIdServices, services);
	}
	@Test
	public void testGetChallengeIdServicesWithChallenges() throws ServletException {
		ParticipantServicesFactory factory = new ParticipantServicesFactory(ctx, ChallengeUriParser.PARTICIPANTSID, challengeId, participantId);
		context.checking(new Expectations () {
			{
				oneOf(ctx).getBean("participantIdServices"); will(returnValue(participantsIdServices));
			}
		});
		ParticipantResourceServices services = factory.getParticipantServices(null);
		assertEquals("should get back correct services", participantsIdServices, services);
	}
}

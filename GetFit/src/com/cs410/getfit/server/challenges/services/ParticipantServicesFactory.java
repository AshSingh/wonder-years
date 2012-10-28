package com.cs410.getfit.server.challenges.services;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;

import com.cs410.getfit.server.challenges.ChallengeUriParser;
import com.cs410.getfit.server.models.ChallengeUser;

public class ParticipantServicesFactory {
	WebApplicationContext ctx;
	ChallengeUriParser parser;

	public ParticipantServicesFactory(WebApplicationContext ctx, ChallengeUriParser parser) {
		this.ctx = ctx;
		this.parser = parser;
	}
	public ParticipantResourceServices getParticipantServices(
			List<ChallengeUser> participants) throws ServletException {
		return getService(participants);
	}

	public ParticipantResourceServices getPaticipantServices() throws ServletException {
		return getService(null);
	}
	private ParticipantResourceServices getParticipantsServices(List<ChallengeUser> participants) {
		ParticipantsServices services = (ParticipantsServices) ctx
				.getBean("participantsServices");
		services.setChallengeId(parser.getChallengeId());
		services.setParticipants(participants);
		return services;
	}

	private ParticipantResourceServices getService(List<ChallengeUser> participants)
			throws ServletException {
		int resource = parser.getResource();
		switch (resource) {
		case ChallengeUriParser.INVALID_URI:
			throw new ServletException("Invalid URI");
		case ChallengeUriParser.PARTICIPANTS:
			return getParticipantsServices(participants);
		case ChallengeUriParser.PARTICIPANTSID:
			return getParticipantIdServices(parser.getChallengeId(),
					parser.getParticipantId(), participants);
		}
		throw new ServletException("Invalid URI");
	}

	private ParticipantResourceServices getParticipantIdServices(
			long challengeId, long participantId,
			List<ChallengeUser> participants) {
		ParticipantIdServices services = (ParticipantIdServices) ctx
				.getBean("participantIdServices");
		services.setParticipantId(participantId);
		if (participants != null && participants.size() == 1)
			services.setParticipant(participants.get(0));
		return services;
	}
}

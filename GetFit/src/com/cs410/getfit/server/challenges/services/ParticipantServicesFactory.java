package com.cs410.getfit.server.challenges.services;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;

import com.cs410.getfit.server.challenges.ChallengeUriParser;
import com.cs410.getfit.server.models.ChallengeUser;
/**
 * A factory to create the participant service based on resource requested
 * @author kiramccoan
 *
 */
public class ParticipantServicesFactory {
	WebApplicationContext ctx;
	int resource;
	long participantId;
	long challengeId;

	public ParticipantServicesFactory(WebApplicationContext ctx, int resource, long challengeId, long participantId) {
		this.ctx = ctx;
		this.resource = resource;
		this.challengeId = challengeId;
		this.participantId = participantId;
	}
	/**
	 * @param participants that the service will act on
	 * @return service with participants
	 * @throws ServletException
	 */
	public ParticipantResourceServices getParticipantServices(
			List<ChallengeUser> participants) throws ServletException {
		return getService(participants);
	}
	/**
	 * @return service to get information about participants from
	 * @throws ServletException
	 */
	public ParticipantResourceServices getPaticipantServices() throws ServletException {
		return getService(null);
	}
	private ParticipantResourceServices getParticipantsServices(List<ChallengeUser> participants) {
		ParticipantsServices services = (ParticipantsServices) ctx
				.getBean("participantsServices");
		services.setChallengeId(challengeId);
		services.setParticipants(participants);
		return services;
	}

	private ParticipantResourceServices getService(List<ChallengeUser> participants)
			throws ServletException {
		switch (resource) {
		case ChallengeUriParser.INVALID_URI:
			throw new ServletException("Invalid URI");
		case ChallengeUriParser.PARTICIPANTS:
			return getParticipantsServices(participants);
		case ChallengeUriParser.PARTICIPANTSID:
			return getParticipantIdServices(challengeId,
					participantId, participants);
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

package com.cs410.getfit.server.challenges.services;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;

import com.cs410.getfit.server.challenges.ChallengeUriParser;
import com.cs410.getfit.server.models.CompletedChallenge;

public class CompletedChallengesServicesFactory {
	WebApplicationContext ctx;
	int resource;
	long challengeId;
	long completedChallengeId;

	public CompletedChallengesServicesFactory(WebApplicationContext ctx,
			int resource, long challengeId, long completedChallengeId) {
		this.ctx = ctx;
		this.resource = resource;
		this.challengeId = challengeId;
		this.completedChallengeId = completedChallengeId;
	}

	public CompletedChallengeResourceServices getCompletedChallengeServices(
			List<CompletedChallenge> challenges) throws ServletException {
		return getService(challenges);
	}

	public CompletedChallengeResourceServices getCompletedChallengeServices()
			throws ServletException {
		return getService(null);
	}

	private CompletedChallengeResourceServices getService(
			List<CompletedChallenge> challenges) throws ServletException {
		switch (resource) {
		case ChallengeUriParser.INVALID_URI:
			throw new ServletException("Invalid URI");
		case ChallengeUriParser.COMPLETEDCHALLENGES:
			return getCompletedChallengesServices(challenges);
		case ChallengeUriParser.COMEPLETEDCHALLENGESSID:
			return getCompletedChallengeIdServices();
		}
		throw new ServletException("Invalid URI for this Factory. Server Error");
	}

	private CompletedChallengeResourceServices getCompletedChallengesServices(
			List<CompletedChallenge> challenges) {
		CompletedChallengesServices services = (CompletedChallengesServices) ctx
				.getBean("completedChallengesService");

		services.setChallenges(challenges);
		services.setChallengeId(challengeId);

		return services;
	}

	private CompletedChallengeResourceServices getCompletedChallengeIdServices() {
		CompletedChallengeIdServices services = (CompletedChallengeIdServices) ctx
				.getBean("completedChallengesIdService");
		services.setCompletedChallengeId(completedChallengeId);
		return services;
	}
}

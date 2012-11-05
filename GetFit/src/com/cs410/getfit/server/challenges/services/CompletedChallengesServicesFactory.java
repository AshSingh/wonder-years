package com.cs410.getfit.server.challenges.services;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;

import com.cs410.getfit.server.challenges.ChallengeUriParser;
import com.cs410.getfit.server.models.CompletedChallenge;

public class CompletedChallengesServicesFactory {
	WebApplicationContext ctx;
	ChallengeUriParser parser;

	public CompletedChallengesServicesFactory(WebApplicationContext ctx, ChallengeUriParser parser) {
		this.ctx = ctx;
		this.parser = parser;
	}

	public CompletedChallengeResourceServices getCompletedChallengeServices(List<CompletedChallenge> challenges) throws ServletException {
		return getService(challenges);
	}

	public CompletedChallengeResourceServices getCompletedChallengeServices()
			throws ServletException {
		return getService(null);
	}
	private CompletedChallengeResourceServices getService(List<CompletedChallenge> challenges)
			throws ServletException {
		int resource = parser.getResource();
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
		CompletedChallengesServices services = (CompletedChallengesServices) ctx.getBean("completedChallengesService");
		
		services.setChallenges(challenges);
		services.setChallengeId(parser.getChallengeId());
		
		return services;
	}

	private CompletedChallengeResourceServices getCompletedChallengeIdServices() {
		CompletedChallengeIdServices services = (CompletedChallengeIdServices) ctx.getBean("completedChallengesIdService");
		services.setCompletedChallengeId(parser.getCompletedChallengeId());		
		return services;
	}
}

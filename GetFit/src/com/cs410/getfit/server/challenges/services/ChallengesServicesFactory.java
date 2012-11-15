package com.cs410.getfit.server.challenges.services;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;

import com.cs410.getfit.server.challenges.ChallengeUriParser;
import com.cs410.getfit.server.models.Challenge;

public class ChallengesServicesFactory {
	WebApplicationContext ctx;
	int resource = -1;
	long challengeId;

	public ChallengesServicesFactory(WebApplicationContext ctx, int resource, long challengeId) {
		this.ctx = ctx;
		this.resource = resource;
		this.challengeId = challengeId;
	}

	public ChallengeResourceServices getChallengeServices(List<Challenge> challenges) throws ServletException {
		return getService(challenges);
	}

	public ChallengeResourceServices getChallengeServices()
			throws ServletException {
		return getService(null);
	}
	private ChallengeResourceServices getService(List<Challenge> challenges)
			throws ServletException {
		switch (resource) {
		case ChallengeUriParser.INVALID_URI:
			throw new ServletException("Invalid URI");
		case ChallengeUriParser.CHALLENGES:
			return getChallengesServices(challenges);
		case ChallengeUriParser.CHALLENGESID:
			return getChallengeIdServices(challengeId, challenges);
		}
		throw new ServletException("Invalid URI for this Factory. Server Error");
	}

	private ChallengeResourceServices getChallengesServices(
			List<Challenge> challenges) {
		ChallengesServices services = (ChallengesServices) ctx.getBean("challengesService");
		
		services.setChallenges(challenges);
		
		return services;
	}

	private ChallengeIdServices getChallengeIdServices(long challengeId,
			List<Challenge> challenges) {
		ChallengeIdServices services = (ChallengeIdServices) ctx.getBean("challengeService");
		
		services.setChallengeId(challengeId);
		if (challenges != null && challenges.size() == 1)
			services.setChallenge(challenges.get(0));
		
		return services;
	}

}

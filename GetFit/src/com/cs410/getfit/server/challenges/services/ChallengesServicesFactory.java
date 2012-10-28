package com.cs410.getfit.server.challenges.services;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;

import com.cs410.getfit.server.challenges.ChallengeUriParser;
import com.cs410.getfit.server.models.Challenge;

public class ChallengesServicesFactory {
	WebApplicationContext ctx;
	ChallengeUriParser parser;

	public ChallengesServicesFactory(WebApplicationContext ctx, ChallengeUriParser parser) {
		this.ctx = ctx;
		this.parser = parser;
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
		int resource = parser.getResource();
		switch (resource) {
		case ChallengeUriParser.INVALID_URI:
			throw new ServletException("Invalid URI");
		case ChallengeUriParser.CHALLENGES:
			return getChallengesServices(challenges);
		case ChallengeUriParser.CHALLENGESID:
			return getChallengeIdServices(parser.getChallengeId(), challenges);
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

package com.cs410.getfit.server.challenges;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cs410.getfit.server.challenges.json.ChallengesJsonFormatter;
import com.cs410.getfit.server.challenges.json.CompletedChallengesJsonFormatter;
import com.cs410.getfit.server.challenges.json.ParticipantJsonFormatter;
import com.cs410.getfit.server.challenges.services.ChallengeResourceServices;
import com.cs410.getfit.server.challenges.services.ChallengesServicesFactory;
import com.cs410.getfit.server.challenges.services.CompletedChallengeResourceServices;
import com.cs410.getfit.server.challenges.services.CompletedChallengesServicesFactory;
import com.cs410.getfit.server.challenges.services.ParticipantResourceServices;
import com.cs410.getfit.server.challenges.services.ParticipantServicesFactory;
import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeUser;
import com.cs410.getfit.server.models.CompletedChallenge;

public class ChallengesServlet extends HttpServlet {

	private static final long serialVersionUID = 8032611514671727168L;

	private WebApplicationContext ctx = null;
	ChallengeUriParser parser;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();
		ctx = WebApplicationContextUtils.getWebApplicationContext(context);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathURI = req.getRequestURI();
		parser = new ChallengeUriParser(pathURI);
		PrintWriter writer = resp.getWriter();
		resp.setHeader("Content-Type", "application/json");

		if (parser.isChallengeURI()) {
			ChallengesJsonFormatter formatter = new ChallengesJsonFormatter();
			ChallengesServicesFactory factory = new ChallengesServicesFactory(
					ctx, parser.getResource(), parser.getChallengeId());
			ChallengeResourceServices services = factory.getChallengeServices();
			List<Challenge> challenges;
			try {
				challenges = services.get();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
			if (challenges != null) {
				writer.write(formatter
						.getJSONFormattedStringOfResource(challenges));
				writer.flush();
				resp.setStatus(200);
			} else {
				resp.setStatus(404); // resource not found
			}
		} else if (parser.isParticipantURI()) {
			ParticipantJsonFormatter formatter = new ParticipantJsonFormatter();
			ParticipantServicesFactory factory = new ParticipantServicesFactory(
					ctx, parser.getResource(), parser.getChallengeId(),
					parser.getParticipantId());
			ParticipantResourceServices services = factory
					.getPaticipantServices();
			List<ChallengeUser> participants;
			try {
				participants = services.get();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
			if (participants != null) {
				writer.write(formatter
						.getJSONFormattedStringOfResource(participants));
				writer.flush();
				resp.setStatus(200);
			} else {
				resp.setStatus(404); // resource not found
			}
		} else if (parser.isCompletedChallengeURI()) {
			CompletedChallengesJsonFormatter formatter = new CompletedChallengesJsonFormatter();
			CompletedChallengesServicesFactory factory = new CompletedChallengesServicesFactory(
					ctx, parser.getResource(), parser.getChallengeId(),
					parser.getCompletedChallengeId());
			CompletedChallengeResourceServices services = factory
					.getCompletedChallengeServices();
			List<CompletedChallenge> c_challenges;
			try {
				c_challenges = services.get();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
			if (c_challenges != null) {
				writer.write(formatter
						.getJSONFormattedStringOfResource(c_challenges));
				writer.flush();
				resp.setStatus(200);
			} else {
				resp.setStatus(404); // resource not found
			}
		} else {
			resp.setStatus(404); // resource not found
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathURI = request.getRequestURI();
		parser = new ChallengeUriParser(pathURI);

		PrintWriter writer = resp.getWriter();
		resp.setHeader("Content-Type", "application/json");

		String requestBody = request.getParameter("json_body");

		if (parser.isChallengeURI()) {
			ChallengesJsonFormatter formatter = new ChallengesJsonFormatter();
			ChallengesServicesFactory factory = new ChallengesServicesFactory(
					ctx, parser.getResource(), parser.getChallengeId());
			final List<Challenge> challenges = formatter
					.getResourcesFromJSONFormattedString(requestBody);
			ChallengeResourceServices services = factory
					.getChallengeServices(challenges);
			List<Challenge> created;
			try {
				created = services.create();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
			if (created != null) {
				writer.write(formatter
						.getJSONFormattedStringOfResource(created));
				writer.flush();
			}
			resp.setStatus(200);
		} else if (parser.isParticipantURI()) {
			ParticipantJsonFormatter formatter = new ParticipantJsonFormatter();
			ParticipantServicesFactory factory = new ParticipantServicesFactory(
					ctx, parser.getResource(), parser.getChallengeId(),
					parser.getParticipantId());
			final List<ChallengeUser> participants = formatter
					.getResourcesFromJSONFormattedString(requestBody);
			ParticipantResourceServices services = factory
					.getParticipantServices(participants);
			List<ChallengeUser> created;
			try {
				created = services.create();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
			if (created != null) {
				writer.write(formatter
						.getJSONFormattedStringOfResource(created));
				writer.flush();
			}
			resp.setStatus(200);
		} else if (parser.isCompletedChallengeURI()) {
			CompletedChallengesJsonFormatter formatter = new CompletedChallengesJsonFormatter();
			CompletedChallengesServicesFactory factory = new CompletedChallengesServicesFactory(
					ctx, parser.getResource(), parser.getChallengeId(), parser.getCompletedChallengeId());
			final List<CompletedChallenge> c_challenges = formatter
					.getResourcesFromJSONFormattedString(requestBody);
			CompletedChallengeResourceServices services = factory
					.getCompletedChallengeServices(c_challenges);
			List<CompletedChallenge> created;
			try {
				created = services.create();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
			if (created != null) {
				writer.write(formatter
						.getJSONFormattedStringOfResource(created));
				writer.flush();
			}
			resp.setStatus(200);
		} else {
			resp.setStatus(404); // resource not found
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathURI = request.getRequestURI();
		parser = new ChallengeUriParser(pathURI);

		resp.setHeader("Content-Type", "application/json");

		String requestBody = request.getParameter("json_body");

		boolean updated = false;
		if (parser.isChallengeURI()) {
			ChallengesJsonFormatter formatter = new ChallengesJsonFormatter();
			ChallengesServicesFactory factory = new ChallengesServicesFactory(
					ctx, parser.getResource(), parser.getChallengeId());
			final List<Challenge> challenges = formatter
					.getResourcesFromJSONFormattedString(requestBody);
			ChallengeResourceServices services = factory
					.getChallengeServices(challenges);

			try {
				updated = services.update();

			} catch (SQLException e) {
				throw new ServletException(e);
			}
		} else if (parser.isParticipantURI()) {
			ParticipantJsonFormatter formatter = new ParticipantJsonFormatter();
			ParticipantServicesFactory factory = new ParticipantServicesFactory(
					ctx, parser.getResource(), parser.getChallengeId(),
					parser.getParticipantId());
			final List<ChallengeUser> participants = formatter
					.getResourcesFromJSONFormattedString(requestBody);
			ParticipantResourceServices services = factory
					.getParticipantServices(participants);

			try {
				updated = services.update();

			} catch (SQLException e) {
				throw new ServletException(e);
			}

		} else {
			resp.setStatus(404); // invalid url
		}
		if (updated) {
			resp.setStatus(200);
		} else {
			resp.setStatus(404); // resource not updated because it doesnt exist
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathURI = request.getRequestURI();
		parser = new ChallengeUriParser(pathURI);

		resp.setHeader("Content-Type", "application/json");
		if (parser.isParticipantURI()) {
			ParticipantServicesFactory factory = new ParticipantServicesFactory(
					ctx, parser.getResource(), parser.getChallengeId(),
					parser.getParticipantId());
			ParticipantResourceServices services = factory.getPaticipantServices();
			try {
				services.delete();
			} catch (SQLException e) {
				throw new ServletException(e);
			}

		} else {
			resp.setStatus(501); // not implemented
		}
	}

}

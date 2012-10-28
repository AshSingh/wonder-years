package com.cs410.getfit.server.challenges;

import java.io.BufferedReader;
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
import com.cs410.getfit.server.challenges.json.ParticipantJsonFormatter;
import com.cs410.getfit.server.challenges.services.ChallengeResourceServices;
import com.cs410.getfit.server.challenges.services.ChallengesServicesFactory;
import com.cs410.getfit.server.challenges.services.ParticipantResourceServices;
import com.cs410.getfit.server.challenges.services.ParticipantServicesFactory;
import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeUser;

public class ChallengesServlet extends HttpServlet {

	private static final long serialVersionUID = 8032611514671727168L;

	private WebApplicationContext ctx = null;

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
		ChallengeUriParser parser = new ChallengeUriParser(pathURI);
		PrintWriter writer = resp.getWriter();
		resp.setHeader("Content-Type", "application/json");

		if (parser.isChallengeURI()) {
			ChallengesJsonFormatter formatter = new ChallengesJsonFormatter(); 
			ChallengesServicesFactory factory = new ChallengesServicesFactory(ctx, parser);
			ChallengeResourceServices services = factory.getChallengeServices();
			List<Challenge> challenges;
			try {
				challenges = services.get();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
			if (challenges != null && challenges.size() > 0) {
				writer.write(formatter.getJSONFormattedStringOfResource(challenges));
				writer.flush();
				resp.setStatus(200);
			} else {
				resp.setStatus(404); // resource not found
			}
		} else if (parser.isParticipantURI()) {
			ParticipantJsonFormatter formatter = new ParticipantJsonFormatter();
			ParticipantServicesFactory factory = new ParticipantServicesFactory(ctx, parser);
			ParticipantResourceServices services = factory.getPaticipantServices();
			List<ChallengeUser> participants;
			try {
				participants = services.get();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
			if (participants != null && participants.size() > 0) {
				writer.write(formatter.getJSONFormattedStringOfResource(participants));
				writer.flush();
				resp.setStatus(200);
			} else {
				resp.setStatus(404); // resource not found
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathURI = request.getRequestURI();
		ChallengeUriParser parser = new ChallengeUriParser(pathURI);
		
		PrintWriter writer = resp.getWriter();
		resp.setHeader("Content-Type", "application/json");
		
		String requestBody = getRequestBody(request);
		
		if(parser.isChallengeURI()) {
			ChallengesJsonFormatter formatter = new ChallengesJsonFormatter();
			ChallengesServicesFactory factory = new ChallengesServicesFactory(ctx, parser);
			final List<Challenge> challenges = formatter.getResourcesFromJSONFormattedString(requestBody);
			ChallengeResourceServices services = factory.getChallengeServices(challenges);
			List<Challenge> created;
			try {
				created = services.create();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
			if (created != null && created.size() > 0) {
				writer.write(formatter.getJSONFormattedStringOfResource(created));
				writer.flush();
				resp.setStatus(201);
				
			} else {
				resp.setStatus(200); // not created
			}
		} else if(parser.isParticipantURI()) {
			ParticipantJsonFormatter formatter = new ParticipantJsonFormatter();
			ParticipantServicesFactory factory = new ParticipantServicesFactory(ctx, parser);
			final List<ChallengeUser> participants = formatter.getResourcesFromJSONFormattedString(requestBody);
			ParticipantResourceServices services = factory.getParticipantServices(participants);
			List<ChallengeUser> created;
			try {
				created = services.create();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
			if (created != null && created.size() > 0) {
				writer.write(formatter.getJSONFormattedStringOfResource(created));
				writer.flush();
				resp.setStatus(201);
				
			} else {
				resp.setStatus(200); // not created
			}
		} else {
			resp.setStatus(404); // resource not found
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathURI = request.getRequestURI();
		ChallengeUriParser parser = new ChallengeUriParser(pathURI);
		
		resp.setHeader("Content-Type", "application/json");
		
		String requestBody = getRequestBody(request);
		
		boolean updated = false;
		if(parser.isChallengeURI()) {
			ChallengesJsonFormatter formatter = new ChallengesJsonFormatter();
			ChallengesServicesFactory factory = new ChallengesServicesFactory(ctx, parser);
			final List<Challenge> challenges = formatter.getResourcesFromJSONFormattedString(requestBody);
			ChallengeResourceServices services = factory.getChallengeServices(challenges);

			try {
				updated = services.update();

			} catch (SQLException e) {
				throw new ServletException(e);
			}
		} else if(parser.isParticipantURI()) {
			ParticipantJsonFormatter formatter = new ParticipantJsonFormatter();
			ParticipantServicesFactory factory = new ParticipantServicesFactory(ctx, parser);
			final List<ChallengeUser> participants = formatter.getResourcesFromJSONFormattedString(requestBody);
			ParticipantResourceServices services = factory.getParticipantServices(participants);
		
			try {
				updated = services.update();

			} catch (SQLException e) {
				throw new ServletException(e);
			}
		
		} else {
			resp.setStatus(404); //invalid url
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
		resp.setStatus(501); // method not implemented
	}

	private String getRequestBody(HttpServletRequest request)
			throws ServletException {
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
			throw new ServletException("Error reading request body: "
					+ e.getMessage());
		}

		return jb.toString();
	}
}

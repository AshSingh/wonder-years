package com.cs410.getfit.server.challenges;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cs410.getfit.server.challenges.json.ChallengesJsonFormatter;
import com.cs410.getfit.server.models.Challenge;

public class ChallengesServlet extends HttpServlet {

	private static final long serialVersionUID = 8032611514671727168L;

	private WebApplicationContext ctx = null;
	private ChallengeResourceServices challengeServices = null;
	private Pattern challengesPattern = Pattern.compile("/challenges");
	private Pattern challengesIdPattern = Pattern
			.compile("/challenges/([0-9]*)");
	private long challengeId = -1;
	private ChallengesJsonFormatter formatter;

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
		challengeServices = getChallengeServices(pathURI);
		formatter = getJsonFormatter(pathURI);

		List<Challenge> challenges;
		try {
			challenges = challengeServices.get(challengeId);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		resp.setHeader("Content-Type", "application/json");
		if(challenges != null && challenges.size() >0) {
			PrintWriter writer = resp.getWriter();
			writer.write(formatter.getJSONFormattedStringOfResource(challenges));
			writer.flush();
			resp.setStatus(200);
		} else {
			resp.setStatus(404); //resource not found
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathURI = request.getRequestURI();
		challengeServices = getChallengeServices(pathURI);
		formatter = getJsonFormatter(pathURI);
		String requestBody = getRequestBody(request);
		final List<Challenge> challenges = formatter
				.getResourcesFromJSONFormattedString(requestBody);
		List<Challenge> created;
		try {
			created = challengeServices.create(challenges);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		if (created != null && created.size() > 0) {
			PrintWriter writer = resp.getWriter();
			writer.write(formatter.getJSONFormattedStringOfResource(created));
			writer.flush();
			resp.setStatus(201); 
			resp.setHeader("Content-Type", "application/json");
		} else {
			resp.setStatus(200); // not created
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathURI = request.getRequestURI();
		challengeServices = getChallengeServices(pathURI);
		formatter = getJsonFormatter(pathURI);
		String requestBody = getRequestBody(request);
		final List<Challenge> challenges = formatter
				.getResourcesFromJSONFormattedString(requestBody);
		boolean updated = false;
		try {
			updated= challengeServices.update(challenges, challengeId);

		} catch (SQLException e) {
			throw new ServletException(e);
		}
		resp.setHeader("Content-Type", "application/json");
		if(updated){
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

	private ChallengesJsonFormatter getJsonFormatter(String pathURI) {
		// do the same thing as the services
		return new ChallengesJsonFormatter();
	}

	private ChallengeResourceServices getChallengeServices(String pathURI)
			throws ServletException {
		Matcher matcher;

		matcher = challengesIdPattern.matcher(pathURI);
		if (matcher.find()) {
			challengeId = Integer.parseInt(matcher.group(1));
			return (ChallengeResourceServices) ctx.getBean("challengeService");
		}

		matcher = challengesPattern.matcher(pathURI);
		if (matcher.find()) {
			return (ChallengeResourceServices) ctx.getBean("challengesService");
		}

		throw new ServletException("Invalid URI");
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

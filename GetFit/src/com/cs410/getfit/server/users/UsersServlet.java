package com.cs410.getfit.server.users;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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

import com.cs410.getfit.server.challenges.json.ChallengeHistoryJsonFormatter;
import com.cs410.getfit.server.challenges.json.ChallengesJsonFormatter;
import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeHistory;
import com.cs410.getfit.server.models.User;
import com.cs410.getfit.server.models.UserImpl;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UsersServlet extends HttpServlet {

	private static final long serialVersionUID = 8032611514671727168L;

	private WebApplicationContext ctx = null;
	private UsersServices usersServices = null;
	private UsersJsonFormatter formatter;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();
		ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		usersServices = (UsersServices) ctx.getBean("usersServices");
		formatter = new UsersJsonFormatter();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		UserUriParser parser = new UserUriParser(req.getRequestURI());
		if(parser.getResource() == UserUriParser.USERS) {
			List<User> users = new ArrayList<User>();
	
			users = usersServices.queryForAllUsers();
	
			PrintWriter writer = resp.getWriter();
			writer.write(formatter.getJSONFormattedStringOfResource(users));
			writer.flush();
	
			// Set the correct status code
			resp.setStatus(200);
		} else if(parser.getResource() == UserUriParser.USERSID) {
			User user = usersServices.getUserById(String.valueOf(parser.getUserId()));
			List <User> users = new ArrayList<User>();
			if(user != null)
				users.add(user);
			PrintWriter writer = resp.getWriter();
			writer.write(formatter.getJSONFormattedStringOfResource(users));
			writer.flush();
	
			// Set the correct status code
			resp.setStatus(200);
		} else if(parser.getResource() == UserUriParser.NEWSFEED) {
			UserNewsfeedServices services = (UserNewsfeedServices) ctx.getBean("userNewsfeedServices");
			services.setUserId(parser.getUserId());
			Long polled = Long.decode(req.getParameter("lastPolled"));
			services.setLastPolled(polled);
			try {
				List<ChallengeHistory> history = services.getChallengeHistory();
				PrintWriter writer = resp.getWriter();
				ChallengeHistoryJsonFormatter formatter = new ChallengeHistoryJsonFormatter();
				writer.write(formatter.getJSONFormattedStringOfResource(history));
				writer.flush();
		
				// Set the correct status code
				resp.setStatus(200);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(parser.getResource() == UserUriParser.CHALLENGES) {
			UserChallengesServices services = (UserChallengesServices) ctx.getBean("userChallengesServices");
			services.setUserId(parser.getUserId());
			try {
				List<Challenge> challenges = services.getChallenges();
				PrintWriter writer = resp.getWriter();
				ChallengesJsonFormatter formatter = new ChallengesJsonFormatter();
				writer.write(formatter.getJSONFormattedStringOfResource(challenges));
				writer.flush();
		
				// Set the correct status code
				resp.setStatus(200);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {

		
		Pattern loginPattern = Pattern
				.compile(".*/login");
		String body = request.getParameter("json_body");
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(body);
		JsonObject jObjBody = null;
		if(element.isJsonObject()) {
			jObjBody = (JsonObject)element;
		}
		
		Matcher matcher;
		matcher = loginPattern.matcher(request.getRequestURI());
		if (matcher.find()) {
			login(request.getParameter("FB_id"), jObjBody,resp);
		} else {
			resp.setStatus(404);
		}
	}
	
	public void login(String fb_id, JsonObject body , HttpServletResponse resp) throws IOException {
		User user = usersServices.getUser(fb_id);
		if(user == null && body != null) {
			String firstname = body.get("first_name").getAsString();
			String lastname = body.get("last_name").getAsString();
			user = new UserImpl(fb_id, firstname, lastname, false);
			usersServices.createUser(user);
		}
		
		Gson gson = new Gson();
		String jsonUser =  gson.toJson(user);
		PrintWriter writer = resp.getWriter();
		writer.write(jsonUser);
		writer.flush();
		resp.setStatus(200);
	}

}

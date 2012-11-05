package com.cs410.getfit.server.users;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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

		//TODO: all the other mappings
		//TODO: error handling!!
		
		if(req.getPathInfo().equals("/users")) {
			List<User> users = new ArrayList<User>();
	
			users = usersServices.queryForAllUsers();
	
			PrintWriter writer = resp.getWriter();
			writer.write(formatter.getJSONFormattedStringOfResource(users));
			writer.flush();
	
			// Set the correct status code
			resp.setStatus(200);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {

		
		String body = request.getParameter("json_body");
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(body);
		JsonObject jObjBody = null;
		if(element.isJsonObject()) {
			jObjBody = (JsonObject)element;
		}
		
		if (request.getRequestURI().equals("/login")){
			login(request.getParameter("FB_id"), jObjBody,resp);
		} else {
			resp.setStatus(404);
		}
		
//		List <User> users = formatter.getResourcesFromJSONFormattedString(body);
//		
//		if(users.size() == 1) {
//			User user = users.get(0); // get first user
//			usersServices.createUser(user);
//
//		} else {
//			//return an error that you cant create more than one user one post.
//		}
//
//		PrintWriter writer = resp.getWriter();
//		writer.write("Created");
//		writer.flush();
//		resp.setStatus(201);

	}
	
	public void login(String fb_id, JsonObject body , HttpServletResponse resp) throws IOException {		
		User user = usersServices.getUser(fb_id);
		if(user == null && body != null) {
			// String username = body.get("username").getAsString();
			String firstname = body.get("first_name").getAsString();
			String lastname = body.get("last_name").getAsString();
			// TODO: Get rid of the username
			user = new UserImpl(fb_id, firstname, lastname, false);
			System.out.println(user.toString());
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

package com.cs410.getfit.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cs410.getfit.shared.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.j256.ormlite.dao.Dao;

public class UsersServlet extends HttpServlet {

	private static final long serialVersionUID = 8032611514671727168L;

	private WebApplicationContext ctx = null;
	private Dao<User, String> userDao = null;

	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();
		ctx = WebApplicationContextUtils.getWebApplicationContext(context);

		userDao = (Dao<User, String>) ctx.getBean("userDao");

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO: remove this code
		System.out.println(req.getMethod());
		System.out.println(req.getRequestURI());

		// TODO: This is for /users
		// TODO: we need to check for /users/<username>

		JsonObject responseObj = new JsonObject();

		List<JsonObject> userObjects = new LinkedList<JsonObject>();
		List<User> users = new ArrayList<User>();
		try {
			users = userDao.queryForAll();
		} catch (SQLException e) {
			e.getMessage();
			// TODO: send back appropriate response
			return;
		}

		for (User user : users) {

			JsonObject userObj = new JsonObject();

			userObj.addProperty(UsersJSONStringFormat.USERNAME, user.getUsername());
			userObj.addProperty(UsersJSONStringFormat.FIRSTNAME, user.getFirstName());
			userObj.addProperty(UsersJSONStringFormat.LASTNAME, user.getLastName());
			userObj.addProperty(UsersJSONStringFormat.PASSWORD, user.getPassword());

			userObjects.add(userObj);
		}

		String userObjectsJson = new Gson().toJson(userObjects);

		responseObj.addProperty(UsersJSONStringFormat.USERS, userObjectsJson);

		PrintWriter writer = resp.getWriter();
		writer.write(responseObj.toString());
		writer.flush();

		// Set the correct status code
		resp.setStatus(200);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = new User();
		user.setFirstName(req.getParameter(UsersJSONStringFormat.FIRSTNAME));
		user.setLastName(req.getParameter(UsersJSONStringFormat.LASTNAME));
		user.setUsername(req.getParameter(UsersJSONStringFormat.USERNAME));
		user.setPassword(req.getParameter(UsersJSONStringFormat.PASSWORD));
		try {
			userDao.create(user);
		} catch (SQLException e) {
			//TODO send by proper response here
			return;
		}

		PrintWriter writer = resp.getWriter();
		writer.write("Created");
		writer.flush();
		resp.setStatus(201);

	}

}

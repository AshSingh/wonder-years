package com.cs410.getfit.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cs410.getfit.shared.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.j256.ormlite.dao.Dao;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -6785962326928820507L;
	WebApplicationContext ctx = null;
	Dao<User, String> userDao = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();
		ctx = WebApplicationContextUtils.getWebApplicationContext(context);

		userDao = (Dao<User, String>) ctx.getBean("userDao");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		StringBuffer jb = new StringBuffer();
		String line = null;

		BufferedReader reader = req.getReader();
		while ((line = reader.readLine()) != null)
			jb.append(line);

		JsonObject jsonObject = new JsonParser().parse(jb.toString()).getAsJsonObject();
		String username = UsersJSONStringFormat.getUsername(jsonObject);
		String pass = UsersJSONStringFormat.getPassword(jsonObject);

		User user = null;
		try {
			user = userDao.queryForId(username);
		} catch (SQLException e) {
			//TODO send by proper response here SQLException
			return;
		}
		if(user == null) {
			//TODO send proper response (user not found)
			return;
		}
		if (user.getPassword().equals(pass)) {
			PrintWriter writer = resp.getWriter();
			writer.write("Success");
			writer.flush();
			resp.setStatus(200);
		} else {
			//TODO send a proper response (password not correct)
		}
	}
}

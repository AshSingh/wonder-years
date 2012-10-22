package com.cs410.getfit.server.users;

import java.io.BufferedReader;
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

import com.cs410.getfit.shared.User;

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
		//TODO: mappings to different get post ect
		StringBuffer jb = new StringBuffer();
		  String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { 
			  /*report an error*/ 
			  }

		String body= jb.toString();
		
		List <User> users = formatter.getResourcesFromJSONFormattedString(body);
		
		if(users.size() == 1) {
			User user = users.get(0); // get first user
			usersServices.createUser(user);

		} else {
			//return an error that you cant create more than one user one post.
		}

		PrintWriter writer = resp.getWriter();
		writer.write("Created");
		writer.flush();
		resp.setStatus(201);

	}

}

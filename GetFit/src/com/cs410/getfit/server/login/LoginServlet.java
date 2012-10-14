package com.cs410.getfit.server.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 8032611514671727168L;

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Storing the session in session table with username to authenticate against");

		PrintWriter writer = resp.getWriter();
		writer.write("Created");
		writer.flush();
		resp.setStatus(201);

	}

}

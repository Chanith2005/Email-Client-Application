package com.xyzemailer.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.xyzemailer.dto.EmailDTO;
import com.xyzemailer.service.EmailService;
import com.xyzemailer.service.UserService;


public class SentServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("email") == null) {
			response.sendRedirect("login");
			return;
		}
		
		String email = (String) session.getAttribute("email");
		
		try {
			List<EmailDTO> sent = EmailService.getSentEmails(UserService.getUserIdByEmail(email));
			request.setAttribute("sent", sent);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "Unable to retrieve emails");
		}
		request.setAttribute("activePage", "sent");
    	request.getRequestDispatcher("/jsp/sent.jsp").forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}

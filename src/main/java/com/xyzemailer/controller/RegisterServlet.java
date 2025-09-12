package com.xyzemailer.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.xyzemailer.dto.UserDTO;
import com.xyzemailer.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);

    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	
    	String firstName = request.getParameter("first-name");
    	String lastName = request.getParameter("last-name");
    	String email = request.getParameter("email");
        String password = request.getParameter("password");
  
        
        
        try {
			if (UserService.existingUser(email)) {
			    request.setAttribute("error", "Email already exists");
			    request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
			    return;
			}
		} catch (SQLException | ServletException | IOException e) {
			e.printStackTrace();
		}
        
        UserDTO userDTO = new UserDTO();
        
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setEmail(email);
        userDTO.setPassword(password);

        
        try {
			if (UserService.registerUser(userDTO)) {
			    response.sendRedirect("jsp/login.jsp?registered=true");
			} else {
			    request.setAttribute("error", "Registration failed");
			    request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
			}
		} catch (SQLException | IOException | ServletException e) {
			e.printStackTrace();
		}
    }
}
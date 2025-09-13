package com.xyzemailer.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import com.xyzemailer.service.EmailService;
import com.xyzemailer.service.UserService;

public class RemoveServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login");
            return;
        }
        
        String action = request.getParameter("action");
        String emailIdString = request.getParameter("emailId");
        String returnPage = request.getParameter("returnPage");
        String userEmail = (String) session.getAttribute("email");
        
        if (emailIdString != null && action != null) {
            try {
                int emailId = Integer.parseInt(emailIdString);
                int userId = UserService.getUserIdByEmail(userEmail);
                
                if ("moveToJunk".equals(action)) {
                    EmailService.moveToJunk(emailId, userId);
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        
        if (returnPage != null) {
            response.sendRedirect(returnPage);
        } else {
            response.sendRedirect("inbox");
        }
    }
}
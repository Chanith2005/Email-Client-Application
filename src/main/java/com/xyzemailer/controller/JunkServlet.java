package com.xyzemailer.controller;

import jakarta.servlet.ServletException;
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

public class JunkServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login");
            return;
        }
        
        String email = (String) session.getAttribute("email");
        
        try {
            int userId = UserService.getUserIdByEmail(email);
            List<EmailDTO> junk = EmailService.getJunkEmails(userId);
            request.setAttribute("junk", junk);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to retrieve junk emails");
        }
        
        request.setAttribute("activePage", "junk");
        request.getRequestDispatcher("/jsp/junk.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login");
            return;
        }
        
        String action = request.getParameter("action");
        String emailIdString = request.getParameter("emailId");
        String userEmail = (String) session.getAttribute("email");
        
        if (emailIdString != null && action != null) {
            try {
                int emailId = Integer.parseInt(emailIdString);
                int userId = UserService.getUserIdByEmail(userEmail);
                
                if ("delete".equals(action)) {
                    EmailService.deleteJunkEmails(emailId, userId);
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        
        response.sendRedirect("junk");
    }
}
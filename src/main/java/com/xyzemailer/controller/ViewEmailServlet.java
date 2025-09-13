package com.xyzemailer.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import com.xyzemailer.dto.EmailDTO;
import com.xyzemailer.service.EmailService;
import com.xyzemailer.service.UserService;

public class ViewEmailServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login");
            return;
        }
        
        String emailIdStr = request.getParameter("id");
        String returnPage = request.getParameter("returnPage");
        String userEmail = (String) session.getAttribute("email");
        
        if (emailIdStr == null) {
            response.sendRedirect("inbox");
            return;
        }
        
        try {
            int emailId = Integer.parseInt(emailIdStr);
            int userId = UserService.getUserIdByEmail(userEmail);
            
            EmailDTO email = EmailService.getEmailById(emailId, userId);
            
            if (email != null) {
                request.setAttribute("email", email);
                request.setAttribute("returnPage", returnPage != null ? returnPage : "inbox");
                request.getRequestDispatcher("/jsp/view-email.jsp").forward(request, response);
            } else {
                response.sendRedirect((returnPage != null ? returnPage : "inbox"));
            }
            
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect((returnPage != null ? returnPage : "inbox"));
        }
    }
}

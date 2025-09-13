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

public class InboxServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login");
            return;
        }
        
        String email = (String) session.getAttribute("email");
        
        try {
            List<EmailDTO> inbox = EmailService.getInboxEmails(UserService.getUserIdByEmail(email));
            request.setAttribute("inbox", inbox);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to retrieve emails");
        }
        
        request.setAttribute("activePage", "inbox");
        request.getRequestDispatcher("/jsp/inbox.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}

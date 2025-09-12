package com.xyzemailer.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import com.xyzemailer.service.EmailService;
import com.xyzemailer.service.UserService;

public class ComposeServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/inbox.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String senderEmail = (String) session.getAttribute("email");
        String recepientEmail = request.getParameter("to");
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");
        String status = request.getParameter("status");

        if (recepientEmail == null || recepientEmail.isEmpty() || subject == null || subject.isEmpty()) {
            request.setAttribute("error", "Recipient and subject are required.");
            request.getRequestDispatcher("/jsp/inbox.jsp").forward(request, response);
            return;
        }

        try {
            if (!UserService.existingUser(recepientEmail)) {
                request.setAttribute("error", "Recipient email does not exist.");
                request.getRequestDispatcher("/jsp/inbox.jsp").forward(request, response);
                return;
            }

            boolean isValid = EmailService.composeEmail(senderEmail, recepientEmail, subject, body, status);
            
            if (isValid) {
                response.sendRedirect(request.getContextPath() + "/inbox");
            } else {
                request.setAttribute("error", "Failed to save the email. Please try again.");
                request.getRequestDispatcher("/jsp/inbox.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred.");
            request.getRequestDispatcher("/jsp/inbox.jsp").forward(request, response);
        }
    }
}

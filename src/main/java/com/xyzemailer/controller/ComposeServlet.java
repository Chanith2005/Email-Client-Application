package com.xyzemailer.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import com.xyzemailer.service.EmailService;

public class ComposeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("inbox");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login");
            return;
        }

        String senderEmail = (String) session.getAttribute("email");
        String recepientEmail = request.getParameter("to");
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");
        String status = request.getParameter("status");

        try {
            boolean result = EmailService.composeEmail(senderEmail, recepientEmail, subject, body, status);
            System.out.println("Mail has been composed");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        if ("draft".equals(status)) {
            response.sendRedirect("draft");
        } else {
            response.sendRedirect("sent");
        }
    }
}
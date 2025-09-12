<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.xyzemailer.dto.EmailDTO" %>
<%@ page import="com.xyzemailer.service.UserService" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>XYZ Mailer - Inbox</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css">
</head>
<body>
    <jsp:include page="sidebar.jsp" flush="true" />

    <div class="main-content">
        <div class="emails-container">
            <h1>Inbox</h1>
            <table class="emails">
                <thead>
                    <tr>
                        <th>From</th>
                        <th>Subject</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<EmailDTO> inbox = (List<EmailDTO>) request.getAttribute("inbox");
                        if (inbox != null && !inbox.isEmpty()) {
                            for (EmailDTO email : inbox) {
                    %>
                    <tr>
                        <td><%= UserService.getEmailById(email.getSenderId()) %></td>
                        <td><%= email.getSubject() %></td>
                        <td><%= email.getSentTime() %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="3">No emails found.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>

    <jsp:include page="compose-popup.jsp" flush="true" />
</body>
</html>

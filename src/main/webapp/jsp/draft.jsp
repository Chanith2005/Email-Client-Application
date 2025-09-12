<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.xyzemailer.dto.EmailDTO" %>
<%@ page import="com.xyzemailer.service.UserService" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>XYZ Mailer - Draft</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css">
</head>
<body>
    <jsp:include page="sidebar.jsp" flush="true" />

    <div class="main-content">
        <div class="emails-container">
            <h1>Sent</h1>
            <table class="emails">
                <thead>
                    <tr>
                        <th>To</th>
                        <th>Subject</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<EmailDTO> draft = (List<EmailDTO>) request.getAttribute("draft");
                        if (draft != null && !draft.isEmpty()) {
                            for (EmailDTO email : draft) {
                    %>
                    <tr>
                        <td><%= UserService.getEmailById(email.getRecepientId()) %></td>
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

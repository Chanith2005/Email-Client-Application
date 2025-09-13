<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.xyzemailer.dto.EmailDTO" %>
<%@ page import="com.xyzemailer.service.UserService" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>XYZ Mailer - Junk</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css">
</head>
<body>
    <jsp:include page="sidebar.jsp" flush="true" />

    <div class="main-content">
        <div class="emails-container">
            <h1>Junk</h1>
            <table class="emails">
                <thead>
                    <tr>
                        <th>From/To</th>
                        <th>Subject</th>
                        <th>Date</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    List<EmailDTO> junk = (List<EmailDTO>) request.getAttribute("junk");
                    if (junk != null && !junk.isEmpty()) {
                        for (EmailDTO email : junk) {
                    %>
                   <tr onclick="window.location.href='${pageContext.request.contextPath}/open?id=<%= email.getEmailId() %>&returnPage=inbox'" style="cursor: pointer;">
					    <td><%= UserService.getEmailById(email.getSenderId()) %></td>
					    <td><%= email.getSubject() %></td>
					    <td><%= email.getSentTime() %></td>
					    <td onclick="event.stopPropagation();">
					        <form method="post" action="${pageContext.request.contextPath}/junk">
					            <input type="hidden" name="emailId" value="<%= email.getEmailId() %>">
					            <input type="hidden" name="action" value="delete">
					            <input type="hidden" name="returnPage" value="junk">
					            <button type="submit">Remove</button>
					        </form>
					    </td>
					</tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="4">No junk emails found.</td>
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
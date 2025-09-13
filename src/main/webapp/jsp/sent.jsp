<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.xyzemailer.dto.EmailDTO" %>
<%@ page import="com.xyzemailer.service.UserService" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>XYZ Mailer - Sent</title>
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
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    List<EmailDTO> sent = (List<EmailDTO>) request.getAttribute("sent");
                    if (sent != null && !sent.isEmpty()) {
                        for (EmailDTO email : sent) {
                    %>
                   <tr onclick="window.location.href='${pageContext.request.contextPath}/open?id=<%= email.getEmailId() %>&returnPage=inbox'" style="cursor: pointer;">
					    <td><%= UserService.getEmailById(email.getRecepientId()) %></td>
					    <td><%= email.getSubject() %></td>
					    <td><%= email.getSentTime() %></td>
					    <td onclick="event.stopPropagation();">
					        <form method="post" action="${pageContext.request.contextPath}/remove">
					            <input type="hidden" name="emailId" value="<%= email.getEmailId() %>">
					            <input type="hidden" name="action" value="moveToJunk">
					            <input type="hidden" name="returnPage" value="sent">
					            <button type="submit">Remove</button>
					        </form>
					    </td>
					</tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="4">No emails found.</td>
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
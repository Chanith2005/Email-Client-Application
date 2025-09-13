<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.xyzemailer.dto.EmailDTO" %>
<%@ page import="com.xyzemailer.service.UserService" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>XYZ Mailer - View</title>
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/view-email.css">
</head>
<body>
    <jsp:include page="sidebar.jsp" flush="true" />

    <div class="main-content">
        <%
        EmailDTO email = (EmailDTO) request.getAttribute("email");
        String returnPage = (String) request.getAttribute("returnPage");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm");
        
        if (email != null) {
        %>
        
  
        <div class="email-view">
            <div class="email-header">
                <div class="email-subject"><%= email.getSubject() != null ? email.getSubject() : "No Subject" %></div>
                <div class="email-meta">
                    <span>
                        <strong>From:</strong> <%= UserService.getEmailById(email.getSenderId()) %>
                        &nbsp;&nbsp;
                        <strong>To:</strong> <%= UserService.getEmailById(email.getRecepientId()) %>
                    </span>
                    <span><%= email.getSentTime() != null ? dateFormat.format(email.getSentTime()) : "" %></span>
                </div>
            </div>
            
            <div class="email-body">
                <%= email.getBody() != null ? email.getBody() : "No content" %>
            </div>
            
            <div class="email-actions">
                <form method="post" action="${pageContext.request.contextPath}/remove" style="display: inline;">
                    <input type="hidden" name="emailId" value="<%= email.getEmailId() %>">
                    <input type="hidden" name="action" value="moveToJunk">
                    <input type="hidden" name="returnPage" value="<%= returnPage %>">
                    <button type="submit" class="remove-delete-button">Move to Junk</button>
                </form>
            </div>
        </div>
        
        <%
        } else {
        %>
        <div class="email-view">
            <h2>Email not found</h2>
            <p>The email you're looking for doesn't exist or you don't have permission to view it.</p>
            <a href="${pageContext.request.contextPath}/inbox" class="back-button">← Back to Inbox</a>
        </div>
        <%
        }
        %>
    </div>
</body>
</html>

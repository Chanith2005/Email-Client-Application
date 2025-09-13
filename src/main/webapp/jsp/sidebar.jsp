<!-- sidebar.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css">
</head>
<body>

    <%
    String activePage = (String) request.getAttribute("activePage");
    if (activePage == null) {
        activePage = "";
    }
    %>
    <div class="sidebar">
        <h2>XYZ Emailer</h2>

        <button class="compose-button" onclick="document.getElementById('composePopup').classList.add('show')">+ Compose</button>

        <ul class="menu">
            <li><a href="inbox" class="<%= "inbox".equals(activePage) ? "active" : "" %>">Inbox</a></li>
            <li><a href="sent" class="<%= "sent".equals(activePage) ? "active" : "" %>">Sent</a></li>
            <li><a href="draft" class="<%= "draft".equals(activePage) ? "active" : "" %>">Draft</a></li>
            <li><a href="junk" class="<%= "junk".equals(activePage) ? "active" : "" %>">Junk</a></li>
            <li><a href="logout">Logout</a></li>
        </ul>
    </div>

</body>
</html>
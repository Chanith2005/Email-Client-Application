<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>XYZ Emailer - Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register-login.css">
</head>
<body>
    <div class="container">
        <div class="login-box">
            <h1>XYZ Mailer</h1>
            <h2>Login</h2>
            
            <% if (request.getParameter("registered") != null) { %>
                <div class="success">Registration successful! Please login.</div>
            <% } %>
            
            <% if (request.getAttribute("error") != null) { %>
                <div class="error"><%= request.getAttribute("error") %></div>
            <% } %>
            
            <form action="login" method="post">
                <div class="form-group">
                    <label>Email:</label>
                    <input type="text" name="email" required>
                </div>
                <div class="form-group">
                    <label>Password:</label>
                    <input type="password" name="password" required>
                </div>
                <button type="submit">Login</button>
            </form>
            
            <p>Don't have an account? <a href="register">Register here</a></p>
        </div>
    </div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>XYZ Mailer - Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register-login.css">
</head>
<body>
    <div class="container">
        <div class="login-box">
            <h1>XYZ Mailer</h1>
            <h2>Register</h2>
            
            <% if (request.getAttribute("error") != null) { %>
                <div class="error"><%= request.getAttribute("error") %></div>
            <% } %>
            
            <form action="register" method="post">
            	<div class="form-group">
                    <label>First Name:</label>
                    <input type="text" name="first-name" required>
                </div>
                <div class="form-group">
                    <label>Last Name:</label>
                    <input type="text" name="last-name" required>
                </div>
                <div class="form-group">
                    <label>Email:</label>
                    <input type="text" name="email" required>
                </div>
                <div class="form-group">
                    <label>Password:</label>
                    <input type="password" name="password" required>
                </div>
                <button type="submit">Register</button>
            </form>
            
            <p>Already have an account? <a href="login">Login here</a></p>
        </div>
    </div>
</body>
</html>
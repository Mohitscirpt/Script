<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Register Startup</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="register-container">
        <form action="RegisterStartupServlet" method="POST">
            <h2>Register Your Startup</h2>
            <label for="name">Startup Name:</label>
            <input type="text" id="name" name="name" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
            
            <button type="submit">Register</button>
        </form>
    </div>
</body>
</html>

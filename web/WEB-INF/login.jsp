<%-- 
    Document   : login
    Created on : Oct 2, 2017, 8:27:43 AM
    Author     : 727525
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Login</h1>
        <form action="Login" method="POST"> 
            Username:
            <input type="text" name="username" value="${username}">
            <br>Password:
            <input type="text" name="password" value="${password}">
            <br>
            <input type="submit" value="Login">
            <br>
            <input type="checkbox" name="rememberMe">Remember Me
        </form>
        <br>${message}
    </body>
</html>

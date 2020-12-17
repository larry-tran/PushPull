<%-- 
    Document   : invalid
    Created on : Nov 1, 2020, 2:19:41 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Invalid Page</title>
    </head>
    <body style="background: powderblue">

    <center>
        <c:if test="${not empty sessionScope.ACCOUNT}">
            <c:if test="${sessionScope.ACCOUNT.role eq 'admin'}">
                <h1>Admin is not allowed to use this function, <a href="rooms.jsp">Back</a></h1>
            </c:if>
        </c:if>
        <c:if test="${empty sessionScope.ACCOUNT}">
            <h1>If you have account please <a href="login.jsp">login</a></h1>
            <h1>If you don't , <a href="signup.jsp">register</a> here</h1>
        </c:if>
    </center>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="com.intetics.lukyanenko.models.Order"%>
<%@ page import="com.intetics.lukyanenko.models.OrderDetail"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store: Your basket</title>
    <base href="<%=request.getContextPath()%>/"/>
</head>
<body>
<form id="confirmation" action="confirm-check-out" method="POST">
Are you sure you want to proceed to checkout content of your basket?
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="Yes" value="">
    <a href="goods">No, continue shopping</a>
    <p><a href="basket">Review basket content</a>
</form>
<a href="">Home page</a>
</body>
</html>
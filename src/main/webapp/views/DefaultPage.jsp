<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store</title>
    <base href="<%=request.getContextPath()%>/"/>
</head>
<body>
<h2>Welcome to store
  <security:authorize access="isAuthenticated()">
    , <security:authentication property="principal.username"/>!
  </security:authorize>
  <security:authorize access="!isAuthenticated()">.</security:authorize>
</h2>
<p><a href="users">List of registered users</a>
<p><a href="category">Goods categories</a>
<p><a href="goods">Goods</a>
</body>
</html>
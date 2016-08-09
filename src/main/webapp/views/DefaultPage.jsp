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
<h2>Welcome to store,
  <security:authorize access="isAuthenticated()">
    <security:authentication property="principal.username"/>
  </security:authorize>
  <security:authorize access="!isAuthenticated()">a guest</security:authorize>
!</h2>
<security:authorize access="hasAuthority('admin')"><p><a href="users">List of registered users</a></security:authorize>
<security:authorize access="hasAuthority('admin')"><p><a href="category">Goods categories</a></security:authorize>
<security:authorize access="hasAuthority('admin')"><p><a href="customer">Customers</a></security:authorize>
<p><a href="goods">Goods</a>
<br>
<security:authorize access="!isAuthenticated()">
You can <a href="login">sign in</a> to see your orders
</security:authorize>
<security:authorize access="isAuthenticated()">
<form action="logout" method="POST">
  <input type="submit" value="Logout">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</security:authorize>
</body>
</html>
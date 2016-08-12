<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.GrantedAuthority" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store: Goods</title>
    <base href="<%=request.getContextPath()%>/"/>
</head>
<body>
  <jsp:include page="../basket/short"/>
  <%@ include file="GoodsSearch.html"%>
<%
  boolean readonly = true;
  for (GrantedAuthority auth: SecurityContextHolder.getContext().getAuthentication().getAuthorities())
    if (auth.getAuthority().equals("admin"))
    {
      readonly = false;
      break;
    }
if(!readonly)
  {%>
<br>
<a href="goods?new">Add a new goods item</a>
<%}%>
  <br><a href="">Home page</a>
</body>
</html>
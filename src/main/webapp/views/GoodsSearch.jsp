<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store: Goods</title>
    <base href="<%=request.getContextPath()%>/"/>
</head>
<body>
  <%@ include file="GoodsSearch.html"%>
<%
  boolean readonly = false; // todo: goods security
if(!readonly)
  {%>
<br>
<a href="goods?new">Add a new goods item</a>
<%}%>
  <br><a href="">Home page</a>
</body>
</html>
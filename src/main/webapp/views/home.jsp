<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%
  HashMap<String, String> model = (HashMap<String, String>)request.getAttribute("home");
  double num = 200.0;
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><%= model.get("title") %></title>
</head>
<body>
<%= model.get("text") %>
</body>
</html>
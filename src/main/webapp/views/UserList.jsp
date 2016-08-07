<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="com.intetics.lukyanenko.models.AppUser" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLEncoder" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store: userlist</title>
    <base href="<%=request.getContextPath()%>/"/>
</head>
<body>
<script>
function getXmlHttp()
{
  var xmlhttp;
  try {
    xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
  } catch (e) {
    try {
      xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    } catch (E) {
      xmlhttp = false;
    }
  }
  if (!xmlhttp && typeof XMLHttpRequest!='undefined') {
    xmlhttp = new XMLHttpRequest();
  }
  return xmlhttp;
}

function requestDelete(name)
{
    if (confirm("Are you sure that you want to delete user \"" + name + "\"?"))
    {
        var xmlhttp = getXmlHttp()
        xmlhttp.open('DELETE', 'users/' + name + '/', false);
        xmlhttp.send(null);
        window.location.href="users";
    }
}
</script>

Registered users:
<table border="1">
  <tr>
    <td>Name</td>
    <td>Customer</td>
    <td></td>
  </tr>
<%
  List<AppUser> userList = (List<AppUser>)request.getAttribute("list");
  for (AppUser appUser: userList)
  {
%>
  <tr>
    <td><a href="users/<%=URLEncoder.encode(appUser.getName(), "UTF-8")%>/"><%=appUser.getName()%></a></td>
    <td><%= (appUser.getIsCustomer() ? "&#10004;" : "")%></td>
    <td><a id="delete" href="" onclick="requestDelete(<%= "'" + appUser.getName() + "'"%>); return false;">delete</a></td>
  </tr>
<%
  }
%>
</table>
<br>
<a href="users?new">Register new user</a>
<br>
<br>
<a href="">Home page</a>
</body>
</html>
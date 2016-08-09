<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="com.intetics.lukyanenko.models.AppUser" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store: Registering a new user</title>
    <base href="<%=request.getContextPath()%>/"/>
</head>
<body>
<%
  AppUser model = (AppUser)request.getAttribute("model");
  String loginName = model.getName();
  if (loginName == null)
  {
    loginName = "";
  }
%>
  <form method="POST" <%=model.getIsNew()?"action=\"users?new\"":""%>>
    <fieldset>
      <legend>
<%if (model.getIsNew())
  {
%>Please provide a new user credentials<%
  } else {
%>You can change password here<%
  }
%>
</legend>
      <table>
        <tr>
          <td><label for="name">User name:</label></td>
          <td><input name="name" class="edit" value="<%=loginName%>" <%=model.getIsNew() ? "" : "disabled=\"true\""%> ></input></td>
        </tr>
        <tr>
          <td><label for="password"><%=model.getIsNew() ? "Password" : "New password"%>:</label></td>
          <td><input name="password" type="password"></input></td>
        </tr>
    </table>
    <input type="submit" value="<%=model.getIsNew() ? "Create user" : "Update password"%>"></input>
    <a href="users"><%=model.getIsNew() ? "Cancel" : "Return to list"%></a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </fieldset>
<%=(model.getIsNew() && model.getName() != null)
   ? String.format("<font color=\"red\">Login name \"%s\" is not available. Please change it and try again</font>", loginName)
   : ""
%>
  </form>
<br>
<a href="">Home page</a>
</body>
</html>
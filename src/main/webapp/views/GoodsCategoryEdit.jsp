<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="com.intetics.lukyanenko.models.GoodsCategory" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<%
  GoodsCategory model = (GoodsCategory)request.getAttribute("model");
  boolean creating = model.getId() == null;
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store: <%= creating ? "Creating a new category" : "Editing a category"%></title>
    <base href="<%=request.getContextPath()%>/"/>
</head>
<body>
  <form method="POST" <%=creating?"action=\"category?new\"":""%>>
    <fieldset>
      <legend>Enter a category name below</legend>
      <table>
        <tr>
          <td><label for="name">category name:</label></td>
          <td><input name="name" class="edit" value="<%= model.getName() == null ? "": model.getName() %>"></input></td>
        </tr>
    </table>
    <input type="submit" value="<%=creating ? "Create a category" : "Update a category"%>"></input>
    <a href="category"><%=creating ? "Cancel" : "Return to list"%></a>
    </fieldset>
<%=(creating && model.getName() != null)
   ? String.format("<font color=\"red\">Category name \"%s\" is not available. Please change it and try again</font>", model.getName())
   : ""
%>
  </form>
<br>
<a href="">Home page</a>
</body>
</html>
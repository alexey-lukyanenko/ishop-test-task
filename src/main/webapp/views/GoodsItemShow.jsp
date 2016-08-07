<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="com.intetics.lukyanenko.models.GoodsItem" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<%
  GoodsItem model = (GoodsItem)request.getAttribute("model");
  boolean creating = model.getId() == null;
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store: <%= creating ? "Creating of a new goods item" : "Editing of a goods item"%></title>
    <base href="<%=request.getContextPath()%>/"/>
</head>
<body>
  <form method="POST" <%=creating? "action=\"goods?new\"":""%>>
    <fieldset>
      <legend>Populate item info</legend>
      <table>
        <tr>
          <td><label for="name">item name:</label></td>
          <td><input id="name" name="name" class="edit" value="<%= model.getName() == null ? "": model.getName() %>"></input></td>
        </tr>
    </table>
    <input type="submit" value="<%=creating ? "Create a item" : "Update a item"%>"></input>
    <a href="item"><%=creating ? "Cancel" : "Return to list"%></a>
    </fieldset>
<%=(creating && model.getName() != null)
   ? String.format("<font color=\"red\">item name \"%s\" is not available. Please change it and try again</font>", model.getName())
   : ""
%>
  </form>
<br>
<a href="">Home page</a>
</body>
</html>
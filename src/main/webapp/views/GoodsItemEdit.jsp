<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="com.intetics.lukyanenko.models.GoodsItem" %>
<%@ page import="com.intetics.lukyanenko.models.GoodsCategory" %>
<%@ page import="java.util.List" %>
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
  <form method="POST" action="<%=creating? "goods?new": String.format("goods/%d", model.getId())%>">
    <fieldset>
      <legend>Populate item info</legend>
      <table>
        <tr>
          <td><label for="name">item name:</label></td>
          <td><input id="name" name="name" class="edit" value="<%= model.getName() == null ? "": model.getName() %>"></td>
        </tr>
        <tr>
          <td><label for="description">Description:</label></td>
          <td><input id="description" name="description" class="edit" value="<%= model.getDescription() == null ? "": model.getDescription() %>"></td>
        </tr>
        <tr>
          <td><label for="price">Price</label></td>
          <td><input id="price" name="price" class="edit" value="<%= model.getName() == null ? "": model.getPrice() %>"></td>
        </tr>
    </table>
<%  for(GoodsCategory category : (List<GoodsCategory>)request.getAttribute("categories"))
    {
      boolean checked = false;
      for(GoodsCategory cat: model.getCategories())
        if (cat.getId().equals(category.getId()))
        {
          checked = true;
          break;
        }
%>
<p> <input type="checkbox"
           name="categories/<%=category.getId()%>"
           value="<%=category.getId()%>"
           <%= checked ? "checked" : "" %>><%=category.getName()%>
<%  }%>
    </fieldset>
    <input type="submit" value="<%=creating ? "Create a item" : "Update a item"%>">
    <a href="goods"><%=creating ? "Cancel" : "Return to list"%></a>
   <%=(creating && model.getName() != null)
   ? String.format("<font color=\"red\">item name \"%s\" is not available. Please change it and try again</font>", model.getName())
   : ""
%>
  </form>
<br>
<a href="">Home page</a>
</body>
</html>
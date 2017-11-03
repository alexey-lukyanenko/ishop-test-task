<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="com.intetics.lukyanenko.models.Customer" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<%
  Customer model = (Customer)request.getAttribute("model");
  boolean creating = model.getId() == null;
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store: <%= creating ? "Creating of a new customer" : "Editing of a customer"%></title>
    <base href="<%=request.getContextPath()%>/"/>
</head>
<body>
<font color="red">
<% String[] errors = (String[])request.getAttribute("errors");
   if (errors != null) {
     for(String error: errors) {
%>
   <%=error%><p>
<% }}%>
</font>
  <form method="POST" action="<%=creating? "goods?new": String.format("goods/%d", model.getId())%>">
    <fieldset>
      <legend>Populate customer info</legend>
      <table>
        <tr>
          <td><label for="name">Customer name:</label></td>
          <td><input id="name" name="fullName" class="edit" value="<%= model.getFullName() == null ? "": model.getFullName() %>"></td>
        </tr>
        <tr>
          <td><label for="phone">Phone:</label></td>
          <td><input id="phone" name="phone" class="edit" value="<%= model.getPhone() == null ? "": model.getPhone() %>"></td>
        </tr>
        <tr>
          <td><label for="email">E-mail</label></td>
          <td><input id="email" name="email" class="edit" value="<%= model.getEmail() == null ? "": model.getEmail() %>"></td>
        </tr>
        <tr>
          <td><label for="address">Shipping address</label></td>
          <td><input id="address" name="shippingAddress" class="edit" value="<%= model.getShippingAddress() == null ? "": model.getShippingAddress() %>"></td>
        </tr>
        <tr>
          <td><label for="login">login-name</label></td>
          <td><input id="login" name="appUserName" class="edit" <%= creating ? "" : "disabled=\"true\""%>
                     value="<%= model.getAppUserName() == null ? "": model.getAppUserName() %>"></td>
        </tr>
<% if (creating)
   { %>
        <tr>
          <td><label for="password">Password</label></td>
          <td><input id="password" name="password" class="password"></td>
        </tr>
        <tr>
          <td><label for="password2">Re-enter password</label></td>
          <td><input id="password2" name="password2" class="password"></td>
        </tr>
<% } %>
      </table>
    </fieldset>
    <input type="submit" value="<%=creating ? "Create" : "Update"%>">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<% String targetPage = (String)request.getAttribute("targetPage");
   if (targetPage != null)
   {
%>
    <input type="hidden" name="targetPage" value="<%=targetPage%>">
    <a href="">
<% } else { %>
    <a href="customer"><%=creating ? "Cancel" : "Return to list"%></a>
<% }%>
  </form>
<br>
<a href="">Home page</a>
</body>
</html>
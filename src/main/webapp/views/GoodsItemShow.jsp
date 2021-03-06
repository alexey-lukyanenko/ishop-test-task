<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="com.intetics.lukyanenko.models.GoodsItem" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<% GoodsItem model = (GoodsItem)request.getAttribute("model"); %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store: <%= model.getName() %></title>
    <base href="<%=request.getContextPath()%>/"/>
</head>
<body>
<script type="text/javascript" src="resources/scripts/xmlhttp.js"></script>
<script>
function requestAddToBasket(id)
{
    var xmlhttp = getXmlHttp()
    xmlhttp.open('POST', 'goods/' + id + '?basket=add', false);
    xmlhttp.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
    xmlhttp.send(null);
    location.reload();
}
</script>
<jsp:include page="../basket/short"/>
<h3>Good details</h3>
<table>
  <tr><td><strong>Name</strong></td><tr>
  <tr><td><%=model.getName()%></td></tr>
  <tr><td><strong>Description</strong></td><tr>
  <tr><td><%=model.getDescription()%></td></tr>
  <tr><td><strong>Price for 1 item</strong></td></tr>
  <tr><td><%=model.getPrice()%></td></tr>
</table>
<form method="POST" action="goods/<%=model.getId()%>?basket=add">
    <input type="button" value="Add to basket" onclick="requestAddToBasket(<%=model.getId()%>); return false;">
</form>
<a href="goods">Return to list</a>
<br>
<a href="">Home page</a>
</body>
</html>
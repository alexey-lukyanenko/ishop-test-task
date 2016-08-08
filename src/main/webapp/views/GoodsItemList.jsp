<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="com.intetics.lukyanenko.models.GoodsItem" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store: Goods</title>
    <base href="<%=request.getContextPath()%>/"/>
</head>
<body>
<script type="text/javascript" src="resources/scripts/xmlhttp.js"></script>
<script>
function requestDelete(name, id)
{
    if (confirm("Are you sure that you want to delete item \"" + name + "\"?"))
    {
        var xmlhttp = getXmlHttp()
        xmlhttp.open('DELETE', 'goods/' + id + '/', false);
        xmlhttp.send(null);
        window.location.href="goods";
    }
}
</script>
<%@ include file="GoodsSearch.html"%>
<%
  List<GoodsItem> list = (List<GoodsItem>)request.getAttribute("list");
  boolean readonly = false; // todo: goods security
  if (!list.isEmpty())
  {
%>
Items:
<table border="1">
  <tr>
    <td>name</td>
    <td>price</td>
    <%=readonly ? "":"<td>action</td>"%>
<%
    for (GoodsItem item: list)
    {
%>
  <tr>
    <td><a href="goods/<%=item.getId()%>/"><%=item.getName()%></a></td>
    <td><%=item.getPrice()%></a></td>
<%
      if(!readonly)
      {
%>
    <td><p><a id="edit" href="goods/<%=item.getId()%>/edit">edit</a>
        <p><a id="delete" href="" onclick="requestDelete(<%= "'" + item.getName() + "'"%>, <%=item.getId()%>); return false;">delete</a></td>
<%    }%>
  </tr>
<%  }%>
</table>
<%}%>
<%if(!readonly)
  {%>
<br>
<a href="goods?new">Add a new goods item</a>
<%}%>
<br>
<a href="">Home page</a>
</body>
</html>
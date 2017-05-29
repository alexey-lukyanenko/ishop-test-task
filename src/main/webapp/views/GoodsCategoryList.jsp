<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="com.intetics.lukyanenko.models.GoodsCategory" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store: categories of goods</title>
    <base href="<%=request.getContextPath()%>/"/>
</head>
<body>
<script type="text/javascript" src="resources/scripts/xmlhttp.js"></script>
<script>
function requestDelete(name, id)
{
    if (confirm("Are you sure that you want to delete category \"" + name + "\"?"))
    {
        var xmlhttp = getXmlHttp()
        xmlhttp.open('DELETE', 'category/' + id + '/', false);
        xmlhttp.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
        xmlhttp.send(null);
        window.location.href="category";
    }
}
</script>
<%
  List<GoodsCategory> list = (List<GoodsCategory>)request.getAttribute("list");
  boolean listOnly = list == null;
  if (listOnly)
    list = (List<GoodsCategory>)request.getAttribute("list_only");
%>
Goods categories:
<table>
<%
  for (GoodsCategory category: list)
  {
%>
  <tr>
    <td><a href="category/<%=category.getId()%>/"><%=category.getName()%></a></td>
<%  if(!listOnly)
    {
%>
    <td><a id="delete" href="" onclick="requestDelete(<%= "'" + category.getName() + "'"%>, <%=category.getId()%>); return false;">delete</a></td>
<%  }%>
  </tr>
<%
  }
%>
</table>
<%if(!listOnly)
  {
%>
<br>
<a href="category?new">Add a new category</a>
<br>
<%}%>
<br>
<a href="">Home page</a>
</body>
</html>
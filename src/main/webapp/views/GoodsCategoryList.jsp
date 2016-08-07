<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="com.intetics.lukyanenko.models.GoodsCategory" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLEncoder" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store: categories of goods</title>
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

function requestDelete(name, id)
{
    if (confirm("Are you sure that you want to delete category \"" + name + "\"?"))
    {
        var xmlhttp = getXmlHttp()
        xmlhttp.open('DELETE', 'category/' + id + '/', false);
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
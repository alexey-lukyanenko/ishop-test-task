<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="com.intetics.lukyanenko.models.GoodsItem" %>
<%@ page import="java.util.List" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.GrantedAuthority" %>
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
        xmlhttp.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
        xmlhttp.send(null);
        window.location.href="goods";
    }
}
</script>
<jsp:include page="../basket/short"/>
<%@ include file="GoodsSearch.html"%>
<%
  List<GoodsItem> list = (List<GoodsItem>)request.getAttribute("list");
  boolean readonly = true;
  for (GrantedAuthority auth: SecurityContextHolder.getContext().getAuthentication().getAuthorities())
    if (auth.getAuthority().equals("admin"))
    {
      readonly = false;
      break;
    }
  //
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
    <td><%=item.getPrice()%></td>
<%
      if(!readonly)
      {
%>
    <td><a id="edit" href="goods/<%=item.getId()%>/edit">edit</a><br>
        <a id="delete" href="" onclick="requestDelete(<%= "'" + item.getName() + "'"%>, <%=item.getId()%>); return false;">delete</a></td>
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
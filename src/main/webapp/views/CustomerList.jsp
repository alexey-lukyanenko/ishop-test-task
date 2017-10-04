<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="com.intetics.lukyanenko.models.Customer" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store: All customers</title>
    <base href="<%=request.getContextPath()%>/"/>
</head>
<body>
<script type="text/javascript" src="resources/scripts/xmlhttp.js"></script>
<script>
function requestDelete(name, id)
{
    if (confirm("Are you sure that you want to delete customer \"" + name + "\"?"))
    {
        var xmlhttp = getXmlHttp()
        xmlhttp.open('DELETE', 'customer/' + id + '/', false);
        xmlhttp.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
        xmlhttp.send(null);
        window.location.href="goods";
    }
}
</script>
<h3>All customers:<h3>
<table border="1">
  <tr>
    <td>Name</td>
    <td>Phone</td>
    <td>E-mail</td>
    <td>Shipping address</td>
    <td>Login-name</td>
    <td></td>
<%
    List<Customer> list = (List<Customer>) request.getAttribute("list");
    for (Customer item: list)
    {
%>
  <tr>
    <td><a href="customer/<%=item.getId()%>/"><%=item.getFullName()%></a></td>
    <td><%=item.getPhone()%></td>
    <td><%=item.getEmail()%></td>
    <td><%=item.getShippingAddress()%></td>
    <td><%=item.getAppUserName()%></td>
    <td><a id="edit" href="customer/<%=item.getId()%>/edit">edit</a><br>
        <a id="delete" href="" onclick="requestDelete(<%= "'" + item.getFullName() + "'"%>, <%=item.getId()%>); return false;">delete</a></td>
  </tr>
<%  }%>
</table>
<br>
<a href="customer?new">Add a new customer</a>
<br>
<a href="">Home page</a>
</body>
</html>
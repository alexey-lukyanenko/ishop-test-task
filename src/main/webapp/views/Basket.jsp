<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="com.intetics.lukyanenko.models.Order"%>
<%@ page import="com.intetics.lukyanenko.models.OrderDetail"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store: Your basket</title>
    <base href="<%=request.getContextPath()%>/"/>
</head>
<body>
<script type="text/javascript" src="resources/scripts/xmlhttp.js"></script>
<script>
function requestDelete(id)
{
    var xmlhttp = getXmlHttp()
    xmlhttp.open('DELETE', 'basket/' + id, false);
    xmlhttp.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
    xmlhttp.send(null);
    if (id == '')
      window.location.href = "goods";
    else
      window.location.href = "basket";
}
</script>
<%  Order basket = (Order)request.getAttribute("basket"); %>
<%  if (basket.getDetails().isEmpty())
    {
%>Your basket is empty
<%  } else { %>
<form id="basketContent" action="basket" method="POST">
    <table border="1" cellpadding="0" cellspacing="0">
      <tr>
        <td></td>
        <td>Item name</td>
        <td>Price for piece</td>
        <td>Quantity</td>
        <td>Subtotal</td>
      </tr>
<%    for(OrderDetail item: basket.getDetails())
      {
%>
      <tr>
        <td><a href="basket" onclick="requestDelete(<%=item.getId()%>); return false;">X</a></td>
        <td><a href="goods/<%=item.getGoodsItem().getId()%>"><%=item.getGoodsItem().getName()%></a></td>
        <td><%=item.getItemPrice()%></td>
        <td><input id="quantity" name="row_<%=item.getId()%>" value="<%=String.format("%1.0f", item.getQuantity())%>"></td>
        <td><%=String.format("%2.2f", item.getQuantity() * item.getItemPrice())%></td>
      </tr>
<%    }%>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="Recalc">
    <a href="goods">Continue shopping</a>
</form>
<br>
<form id="checkout" action="basket/checkout" method="POST">
  <input type="submit" value="Proceed for checkout">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>
<form id="clear" method="GET">
  <input type="button" value="Clear basket" onclick="requestDelete(''); return false;">
</form>
<%}%>
<br>
<a href="">Home page</a>
</body>
</html>
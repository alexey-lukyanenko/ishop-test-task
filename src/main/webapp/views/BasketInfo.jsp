<%@ page import="com.intetics.lukyanenko.models.Order"%>
<div id="basket">
<%
  Order basket = (Order)request.getAttribute("basket");
  int size = basket.getDetails().size();
%>
<a href="${pageContext.request.contextPath}/basket">Your basket's subtotal (<%=size%> <%= size > 1 ? "items" : "item"%>) is <%=basket.getTotal()%></a>
</div>
<hr>
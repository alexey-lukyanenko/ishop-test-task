<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@ page import="com.intetics.lukyanenko.models.AppUser" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internet-store: Registering a new user</title>
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

function requestDelete(name)
{
    if (confirm("Are you sure that you want to delete user \"" + name + "\"?"))
    {
        var xmlhttp = getXmlHttp()
        xmlhttp.open('DELETE', 'users/' + name, false);
        xmlhttp.send(null);
        if(xmlhttp.status == 200)
        {
          location.reload();
        }
    }
}
</script>
<%
  AppUser model = (AppUser)request.getAttribute("model");
  String formMethod;
  String loginName = model.getName();
  boolean creating = false;
  if (loginName == null)
  {
    formMethod = "PUT";
    loginName = "";
    creating = true;
  }
  else
    formMethod = "POST";
%>
<% if (creating)
   {
%><h4>Please provide a new user credentials</h4><%
   }
   else
   {
%><h4>You can chage password here</h4><%
   }
%>
  <form method="<%=formMethod%>">
    <table>
      <div id="appUser">
        <tr>
          <td><label for="name">User name:</label></td>
          <td><input id="name" class="edit" value="<%=loginName%>" <%=creating ? "" : "disabled=\"true\""%> ></input></td>
        </tr>
        <tr>
          <td><label for="password"><%=creating ? "Password" : "New password"%>:</label></td>
          <td><input id="password" type="password"></input></td>
        </tr>
      </div>
    </table>
    <input type="submit" value="<%=creating ? "Create user" : "Update password"%>"></input>
    <a href="users"><%=creating ? "Cancel" : "Return to list"%></a>
  </form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
    String email = (String) session.getAttribute("email");
    String name = (String) session.getAttribute("name");

    if (email == null) {
        response.sendRedirect("login.html");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dashboard</title>
</head>
<body>

<h2>Welcome, <%= name %></h2>


<br>

<a href="deposit.html">Deposit Money</a><br><br>
<a href="withdraw.html">Withdraw Money</a><br><br>
<a href="transfer.html">Transfer Money</a><br><br>
<a href="ViewServlet">View Balance</a><br><br>
<a href="viewall.jsp">View All Accounts</a><br><br>
<a href="LogoutServlet">Logout</a>

</body>
</html>
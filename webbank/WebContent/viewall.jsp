<%@ page import="java.sql.*" %>
<%@ page import="webbank.ConnectDB" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
    String email = (String) session.getAttribute("email");
    if (email == null) {
        response.sendRedirect("login.html");
        return;
    }
    String keyword = request.getParameter("keyword");
%>
<!DOCTYPE html>
<html>
<head>
    <title>View All Accounts</title>
</head>
<body>
<h2>All Bank Accounts</h2>

<form action="viewall.jsp" method="get">
    <input type="text" name="keyword" placeholder="Search by account number or email"
           value="<%= keyword != null ? keyword : "" %>" style="padding:6px; width:250px;">
    <input type="submit" value="Search">
    <% if (keyword != null && !keyword.trim().isEmpty()) { %>
        <a href="viewall.jsp">Clear</a>
    <% } %>
</form>
<br>

<table border="1" cellpadding="10">
    <tr>
    <th>Account Number</th>
    <th>Name</th>
    <th>Email</th>
    <th>Delete</th>
    <th>Balance</th>
	</tr>
<%
    try {
        Connection con = ConnectDB.getConnection();
        PreparedStatement ps;

        if (keyword != null && !keyword.trim().isEmpty()) {
            int accountNo;
            try {
                accountNo = Integer.parseInt(keyword.trim());
            } catch (Exception e) {
                accountNo = -1;
            }
            ps = con.prepareStatement(
                "SELECT id, name, email, balance FROM users WHERE id=? OR email=?"
            );
            ps.setInt(1, accountNo);
            ps.setString(2, keyword.trim());
        } else {
            ps = con.prepareStatement("SELECT id, name, email, balance FROM users");
        }

        ResultSet rs = ps.executeQuery();
        boolean found = false;
        while (rs.next()) {
            found = true;
%>
    <tr>
    <td><%= rs.getInt("id") %></td>
    <td><%= rs.getString("name") %></td>
    <td><%= rs.getString("email") %></td>
    <td>
        <form action="DeleteServlet" method="post">
            <input type="hidden" name="id" value="<%= rs.getInt("id") %>">
            <input type="submit" value="Delete">
        </form>
    </td>
    <td><%= rs.getDouble("balance") %></td>
</tr>
<%
        }
        if (!found) {
%>
    <tr><td colspan="5">No matching account found.</td></tr>
<%
        }
    } catch (Exception e) {
        out.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
%>
</table>
<br>
<a href="dashboard.jsp">Back to Dashboard</a>
</body>
</html>
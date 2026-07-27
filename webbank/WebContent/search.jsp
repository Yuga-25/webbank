<%@ page import="java.sql.*" %>
<%@ page import="webbank.ConnectDB" %>

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
    <title>Search Account</title>
</head>
<body>

<h2>Search Result</h2>

<%
    try {
        Connection con = ConnectDB.getConnection();

        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM users WHERE id=? OR email=?"
        );

        int accountNo = 0;

        try {
            accountNo = Integer.parseInt(keyword);
        } catch (Exception e) {
            accountNo = -1;
        }

        ps.setInt(1, accountNo);
        ps.setString(2, keyword);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
%>

<table border="1" cellpadding="10">
    <tr>
        <th>Account Number</th>
        <th>Name</th>
        <th>Email</th>
        <th>Balance</th>
    </tr>

    <tr>
        <td><%= rs.getInt("id") %></td>
        <td><%= rs.getString("name") %></td>
        <td><%= rs.getString("email") %></td>
        <td><%= rs.getDouble("balance") %></td>
    </tr>
</table>

<%
        } else {
            out.println("<p>No account found.</p>");
        }

    } catch (Exception e) {
        out.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
%>

<br>
<a href="dashboard.jsp">Back to Dashboard</a>

</body>
</html>
package webbank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewServlet")
public class ViewServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String email = (String) session.getAttribute("email");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Connection con = ConnectDB.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE email=?"
            );

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<h2>Account Details</h2>");
                out.println("Name: " + rs.getString("name") + "<br>");
                out.println("Email: " + rs.getString("email") + "<br>");
                out.println("Balance: " + rs.getDouble("balance") + "<br><br>");
                out.println("<a href='dashboard.jsp'>Back</a>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}
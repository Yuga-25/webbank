package webbank;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String email = (String) session.getAttribute("email");
        double amount = Double.parseDouble(request.getParameter("amount"));

        try {
            Connection con = ConnectDB.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "UPDATE users SET balance = balance + ? WHERE email=?"
            );

            ps.setDouble(1, amount);
            ps.setString(2, email);

            int i = ps.executeUpdate();

            if (i > 0) {
                response.sendRedirect("ViewServlet");
            } else {
                response.getWriter().println("Deposit failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
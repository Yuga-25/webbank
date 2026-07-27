package webbank;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {
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
            PreparedStatement check = con.prepareStatement(
                "SELECT balance FROM users WHERE email=?"
            );
            check.setString(1, email);
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance >= amount) {
                    PreparedStatement ps = con.prepareStatement(
                        "UPDATE users SET balance = balance - ? WHERE email=?"
                    );
                    ps.setDouble(1, amount);
                    ps.setString(2, email);
                    ps.executeUpdate();
                    response.sendRedirect("success.html");
                } else {
                    response.sendRedirect("failed.html");
                }
            } else {
                response.sendRedirect("failed.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("failed.html");
        }
    }
}
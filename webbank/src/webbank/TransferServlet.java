package webbank;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TransferServlet
 */
public class TransferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransferServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**package webbank;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/TransferServlet")
public class TransferServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String senderEmail = (String) session.getAttribute("email");
        String receiverEmail = request.getParameter("receiverEmail");
        double amount = Double.parseDouble(request.getParameter("amount"));

        if (senderEmail.equals(receiverEmail)) {
            response.getWriter().println("You cannot transfer money to yourself");
            return;
        }

        try {
            Connection con = ConnectDB.getConnection();

            PreparedStatement checkSender = con.prepareStatement(
                "SELECT balance FROM users WHERE email=?"
            );
            checkSender.setString(1, senderEmail);
            ResultSet senderRs = checkSender.executeQuery();

            if (!senderRs.next()) {
                response.getWriter().println("Sender account not found");
                return;
            }

            double senderBalance = senderRs.getDouble("balance");

            if (senderBalance < amount) {
                response.getWriter().println("Insufficient balance");
                return;
            }

            PreparedStatement checkReceiver = con.prepareStatement(
                "SELECT balance FROM users WHERE email=?"
            );
            checkReceiver.setString(1, receiverEmail);
            ResultSet receiverRs = checkReceiver.executeQuery();

            if (!receiverRs.next()) {
                response.getWriter().println("Receiver account not found");
                return;
            }

            PreparedStatement withdraw = con.prepareStatement(
                "UPDATE users SET balance = balance - ? WHERE email=?"
            );
            withdraw.setDouble(1, amount);
            withdraw.setString(2, senderEmail);
            withdraw.executeUpdate();

            PreparedStatement deposit = con.prepareStatement(
                "UPDATE users SET balance = balance + ? WHERE email=?"
            );
            deposit.setDouble(1, amount);
            deposit.setString(2, receiverEmail);
            deposit.executeUpdate();

            response.sendRedirect("ViewServlet");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

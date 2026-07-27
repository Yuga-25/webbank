package webbank;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Connection con = ConnectDB.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM users WHERE id=?"
            );

            ps.setInt(1, id);

            int result = ps.executeUpdate();

            if (result > 0) {
                response.sendRedirect("success.html");
            } else {
                response.sendRedirect("failed.html");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("failed.html");
        }
    }
}
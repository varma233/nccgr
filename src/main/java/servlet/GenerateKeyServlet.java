package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DBUtil;
import utils.OTP;

public class GenerateKeyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	private PreparedStatement ps;
       
    public GenerateKeyServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside GenerateKeyServlet...");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String note = request.getParameter("note");

		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement("insert into auth_keys(key, note) values(?,?)");

			String key = OTP.getOTP();
			ps.setString(1, key);
			ps.setString(2, note);

			int i = ps.executeUpdate();
			
			System.out.println("NUMBER:"+i);
			if (i > 0) {
				request.setAttribute("key", key);
				request.getRequestDispatcher("generatekey.jsp").forward(request, response);
			}else{
				request.setAttribute("key", null);
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Unable to generate KEY !');");
				out.println("location='generatekey.jsp';");
				out.println("</script>");
			}
		} catch (Exception e2) {
			out.print("<h2> <font color=red>Oops! Something went wrong. Please try again...  </font><h2>");
			System.out.println(e2);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		out.close();
	}

}

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

public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	private PreparedStatement ps;
	
	public HomeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Inside HomeServlet...");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String name = request.getParameter("name");
		String city = request.getParameter("city");
		String location = request.getParameter("location");

		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement("insert into svvgr(name,city,location) values(?,?,?)");

			ps.setString(1, name);
			ps.setString(2, city);
			ps.setString(3, location);

			int i = ps.executeUpdate();
			if (i > 0) {
				response.sendRedirect("home.jsp");
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Details submitted Sucessfully!');");
				out.println("location='index.jsp';");
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

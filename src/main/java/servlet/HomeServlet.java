package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name = "HomeServlet", urlPatterns = { "/HomeServlet" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	String dbURL;
	String dbUsername;
	String dbPassword;

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
			Class.forName("org.postgresql.Driver");
			
			String DATABASE_URL = System.getenv("DATABASE_URL");
			
			if (DATABASE_URL == null )
				DATABASE_URL = getServletContext().getInitParameter("DATABASE_URL");
			
			URI dbUri = new URI(DATABASE_URL);	
			
			dbUsername = dbUri.getUserInfo().split(":")[0];
			dbPassword = dbUri.getUserInfo().split(":")[1];
			dbURL = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()
					+ "?sslmode=require";

			con = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
			PreparedStatement ps = con.prepareStatement("insert into svvgr(name,city,location) values(?,?,?)");

			ps.setString(1, name);
			ps.setString(2, city);
			ps.setString(3, location);

			int i = ps.executeUpdate();
			if (i > 0) {
//				request.getRequestDispatcher("home.jsp").forward(request, response);
				response.sendRedirect("home.jsp");
//				out.print("<h2> <font color=green> Details submitted Sucessfully!  </font><h2>");
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Details submitted Sucessfully!');");
				out.println("location='index.jsp';");
				out.println("</script>");
			}
		} catch (Exception e2) {
//			response.sendRedirect("home.jsp");
			out.print("<h2> <font color=red>Oops! Something went wrong. Please try again...  </font><h2>");
			System.out.println(e2);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		out.close();
	}
}

package servlet.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.Encrypt;

//@WebServlet(name = "LoginServlet", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection con;
	String dbURL;
	String dbUsername;
	String dbPassword;
	ResultSet rs;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("Inside LoginServlet...");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");

		/**
		 * input validations
		 * 
		 * TODO
		 * 
		 **/

		try {
			Class.forName("org.postgresql.Driver");

			String DATABASE_URL = System.getenv("DATABASE_URL");

			if (DATABASE_URL == null)
				DATABASE_URL = getServletContext().getInitParameter("DATABASE_URL");

			URI dbUri = new URI(DATABASE_URL);

			dbUsername = dbUri.getUserInfo().split(":")[0];
			dbPassword = dbUri.getUserInfo().split(":")[1];
			dbURL = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()
					+ "?sslmode=require";

			con = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
			String sql = "SELECT PASSWORD, FIRST_NAME, MOBILE_NUMBER FROM USERS WHERE MOBILE_NUMBER = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, mobile);


			rs = ps.executeQuery();
			if (rs.next()) {
				if (Encrypt.validatePassword(password, rs.getString(1))) {
					request.setAttribute("username", rs.getString(2));
					HttpSession session=request.getSession();  
			        session.setAttribute("mobile", rs.getString(3));
					request.getRequestDispatcher("home.jsp").include(request, response);
//					response.sendRedirect("home.jsp");
			        
				}else {
					out.println("<script type=\"text/javascript\">");
					out.println("alert('Wrong password. Please verify... !');");
					out.println("location='index.jsp';");
					out.println("</script>");
				}
			} else {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Wrong Mobile Number. Please verify... !');");
				out.println("location='index.jsp';");
				out.println("</script>");
			}

		} catch (Exception e2) {
			out.print("<h2> <font color=red>Oops..! Something went wrong...please try again</font><h2>");
			System.out.println(e2);
		} finally {
			try {
				if (rs != null)
					rs.close();

				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}

		out.close();
	}

}

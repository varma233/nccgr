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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.Encrypt;

public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	String dbURL;
	String dbUsername; 
	String dbPassword;
	ResultSet rs;
	PreparedStatement ps;

	public ChangePasswordServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		System.out.println("Inside ChangePasswordServlet...");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");

		HttpSession session = request.getSession();
		String mobile = (String) session.getAttribute("mobile");

		try {

			if (mobile == null || mobile.length() == 0) {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Your session expired. Please login again... !');");
				out.println("location='index.jsp';");
				out.println("</script>");
			} else {

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
				String sql = "SELECT PASSWORD FROM USERS WHERE MOBILE_NUMBER = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, mobile);

				rs = ps.executeQuery();
				if (rs.next()) {
					if (Encrypt.validatePassword(oldpassword, rs.getString(1))) {
						sql = "UPDATE USERS SET PASSWORD = ? WHERE MOBILE_NUMBER= ? ;";
						ps.close();
						ps = con.prepareStatement(sql);
						ps.setString(1, Encrypt.generateStorngPasswordHash(newpassword));
						ps.setString(2, mobile);
						
						int count = ps.executeUpdate();
						System.out.println(count);
						if (count > 0 ) {	
							out.println("<script type=\"text/javascript\">");
							out.println("alert('Password Changed Successfully... !');");
							out.println("location='home.jsp';");
							out.println("</script>");
						}
						
					} else {
						out.println("<script type=\"text/javascript\">");
						out.println("alert('PLease correct old password... !');");
						out.println("location='changepassword.jsp';");
						out.println("</script>");
					}
				}

			}
		} catch (Exception e2) {
			out.print("<h2> <font color=red>Oops..! Something went wrong...please try again</font><h2>");
			System.out.println(e2);
		} finally {
			try {
				
				if(ps!=null)
					ps.close();
				
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

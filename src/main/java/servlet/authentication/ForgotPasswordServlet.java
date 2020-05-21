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

import utils.Encrypt;
import utils.Util;
import utils.sms.SendSMS;

public class ForgotPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	String dbURL;
	String dbUsername;
	String dbPassword;
	ResultSet rs;
	PreparedStatement ps;

	public ForgotPasswordServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Inside ForgotPasswordServlet...");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String OTP = Util.getOTP();
		String newPassword = "Nccgr" + OTP;
		String mobile = request.getParameter("mobile");

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
			ps = con.prepareStatement("UPDATE USERS SET PASSWORD = ? WHERE MOBILE_NUMBER= ? ;");
			ps.setString(1, Encrypt.generateStorngPasswordHash(newPassword));
			ps.setString(2, mobile);

			int count = ps.executeUpdate();
			System.out.println(count);

			if (count > 0) {

				try {
					SendSMS.send("+91" + mobile, "Your new password is:\n" + newPassword);

					try {
						utils.Email.send();
						System.out.println("Email sent succesfully");
					} catch (Exception e) {
						System.out.println("Unable to send email");
						System.out.println(e.toString());					
					}
					
					request.setAttribute("PasswordResetStatus", "success");
					out.println("<script type=\"text/javascript\">");
					out.println("alert('Please login with new password sent to your mobile " + mobile + "');");
					out.println("location='index.jsp';");
					out.println("</script>");

					

				} catch (Exception e) {
					System.out.println(e.toString());
					request.setAttribute("PasswordResetStatus", "failure");
					request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
				}

			} else {
				request.setAttribute("PasswordResetStatus", "failure");
				request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
			}

		} catch (Exception e2) {
			System.out.println(e2);
			request.setAttribute("PasswordResetStatus", "failure");
			request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
		} finally {
			try {

				if (ps != null)
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

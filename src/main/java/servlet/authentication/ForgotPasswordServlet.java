package servlet.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DBUtil;
import utils.Encrypt;
import utils.OTP;
import utils.SMS;

public class ForgotPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;

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

		String otp = OTP.getOTP();
		String newPassword = "Nccgr" + otp;
		String mobile = request.getParameter("mobile");

		try {
			con = DBUtil.getConnection();

			String sql = "SELECT EMAILID  FROM USERS WHERE MOBILE_NUMBER = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, mobile);

			rs = ps.executeQuery();
			if (rs.next()) {
				String email = rs.getString(1);

				ps = con.prepareStatement("UPDATE USERS SET PASSWORD = ? WHERE MOBILE_NUMBER= ? ;");
				ps.setString(1, Encrypt.generateStorngPasswordHash(newPassword));
				ps.setString(2, mobile);

				int count = ps.executeUpdate();
				System.out.println(count);

				if (count > 0) {

					try {
						SMS.send("+91" + mobile, "Your new password is:\n" + newPassword);

						try {
							utils.Email.send(email, "Your new password is : " + newPassword);
							System.out.println("Email sent succesfully");
						} catch (Exception e) {
							System.out.println("Unable to send email");
							System.out.println(e.toString());
						}

						request.setAttribute("PasswordResetStatus", "success");
						out.println("<script type=\"text/javascript\">");
						out.println("alert('Please login with new password sent to your mobile/email);");
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
			}

		} catch (Exception e2) {
			System.out.println(e2);
			request.setAttribute("PasswordResetStatus", "failure");
			request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);

		} finally {
			try {
				if (rs != null)
					rs.close();
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

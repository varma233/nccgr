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

public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public SignupServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("index.jsp");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Inside SignupServlet...");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String mobile = request.getParameter("mobile");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String authkey = request.getParameter("authkey");

		try {
			con = DBUtil.getConnection();
			String sql = "select * from auth_keys where key = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, authkey);
			rs = ps.executeQuery();

			if (!rs.next()) {
				request.setAttribute("registrationstatus", "failure");
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Invalid AUTHORISATION KEY. Please contact Admin to get new key !');");
				out.println("location='signup.jsp';");
				out.println("</script>");
			} else {
				sql = "insert into users(first_name, last_name, mobile_number, emailid, password) values(?,?,?,?,?)";
				ps = con.prepareStatement(sql);

				ps.setString(1, firstname);
				ps.setString(2, lastname);
				ps.setString(3, mobile);
				ps.setString(4, email);
				ps.setString(5, Encrypt.generateStorngPasswordHash(password));
				
				int i = ps.executeUpdate();
				if (i > 0) {
					sql = "delete from auth_keys where key = ?";
					ps = con.prepareStatement(sql);
					ps.setString(1, authkey);
					int deletedrows = ps.executeUpdate();
					if (deletedrows > 0) {
						request.setAttribute("registrationstatus", "success");
						request.getRequestDispatcher("index.jsp").forward(request, response);
					} else {
						request.setAttribute("registrationstatus", "failure");
						request.getRequestDispatcher("signup.jsp").forward(request, response);
					}
				} else {
					request.setAttribute("registrationstatus", "failure");
					request.getRequestDispatcher("signup.jsp").forward(request, response);
				}
			}
		} catch (Exception e2) {
			request.setAttribute("registrationstatus", "failure");
			request.getRequestDispatcher("signup.jsp").forward(request, response);
			System.out.println(e2);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if(ps!=null)
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

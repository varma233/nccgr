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
import javax.servlet.http.HttpSession;

import utils.DBUtil;
import utils.Encrypt;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	

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

		try {
			con = DBUtil.getConnection();
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

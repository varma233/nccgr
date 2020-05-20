package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GenerateKeyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	String dbURL;
	String dbUsername;
	String dbPassword;
       
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
			PreparedStatement ps = con.prepareStatement("insert into auth_keys(key, note) values(?,?)");

			Random generator = new Random();
			generator.setSeed(System.currentTimeMillis());			  
			String key = (generator.nextInt(899999) + 100000)+"";
			
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

package servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DownloadServlet extends HttpServlet {

	Connection con;
	Statement statement;
	ResultSet resultSet;
	FileOutputStream out;
	XSSFWorkbook workbook;
	XSSFSheet spreadsheet;
	XSSFRow row;
	XSSFCell cell;

	String dbURL;
	String dbUsername;
	String dbPassword;

	private static final long serialVersionUID = 1L;

	public DownloadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		prepareExcelFile(response);

	}

	private void prepareExcelFile(HttpServletResponse response) {
		System.out.println("Inside DownloadServlet...");

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

			statement = con.createStatement();
			
//			String sql = "SELECT ID, NAME, CITY, LOCATION, CREATED_AT :: TIMESTAMP AT TIME ZONE 'Asia/Kolkata' AS \"CREATED_AT\", CREATED_BY "
//					+ "  FROM SVVGR ORDER BY CREATED_AT DESC;";
			String sql = "SELECT ID, NAME, CITY, LOCATION, CREATED_AT, CREATED_BY FROM SVVGR ORDER BY CREATED_AT DESC;";
			
			
			resultSet = statement.executeQuery(sql);

			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=exceldata.xlsx");

			workbook = new XSSFWorkbook();
			spreadsheet = workbook.createSheet("svvgr");

			row = spreadsheet.createRow(0);

			// bold font for header
			CellStyle style = workbook.createCellStyle();// Create style
			Font font = workbook.createFont();// Create font
			font.setBold(true);// Make font bold
			font.setColor(Font.COLOR_RED);
			style.setFont(font);// set it to bold
			
			
			//date format MM/dd/yyyy hh:mm a Z
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
//			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

			cell = row.createCell(0);
			cell.setCellValue("ID");
			cell.setCellStyle(style);
			
			cell = row.createCell(1);
			cell.setCellValue("NAME");
			cell.setCellStyle(style);
			
			cell = row.createCell(2);
			cell.setCellValue("CITY");
			cell.setCellStyle(style);
			
			cell = row.createCell(3);
			cell.setCellValue("LOCATION");
			cell.setCellStyle(style);
			
			cell = row.createCell(4);
			cell.setCellValue("CREATION TIME");
			cell.setCellStyle(style);
			
			cell = row.createCell(5);
			cell.setCellValue("CREATED BY");
			cell.setCellStyle(style);

			int i = 1;

			while (resultSet.next()) {
				row = spreadsheet.createRow(i);
				cell = row.createCell(0);
				cell.setCellValue(resultSet.getInt("ID"));
				cell = row.createCell(1);
				cell.setCellValue(resultSet.getString("NAME"));
				cell = row.createCell(2);
				cell.setCellValue(resultSet.getString("CITY"));
				cell = row.createCell(3);
				cell.setCellValue(resultSet.getString("LOCATION"));
				cell = row.createCell(4);
				cell.setCellValue(sdf.format(resultSet.getTimestamp("CREATED_AT")));
				
				cell = row.createCell(5);
				cell.setCellValue(resultSet.getString("CREATED_BY"));

				i++;
			}

			// Auto adjusting column widths
			for (int j = 0; j < 5; j++)
				spreadsheet.autoSizeColumn(j, false);

			workbook.write(response.getOutputStream());

			response.getOutputStream().flush();
			response.getOutputStream().close();
			workbook.close();

			System.out.println("exceldatabase.xlsx written successfully");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (resultSet != null)
					resultSet.close();
				if (con != null)
					con.close();
				if (out != null)
					out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//doGet(request, response);
		response.sendRedirect("index.jsp");
	}

}

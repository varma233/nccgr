package utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static String driverName = "org.postgresql.Driver";
	private static String dbUsername;
	private static String dbPassword;
	private static String dbURL;

	public static Connection getConnection() throws URISyntaxException {

		try {
			Class.forName(driverName);
			try {
				String DATABASE_URL = System.getenv("DATABASE_URL");
				URI dbUri = new URI(DATABASE_URL);
				dbUsername = dbUri.getUserInfo().split(":")[0];
				dbPassword = dbUri.getUserInfo().split(":")[1];
				dbURL = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()
						+ "?sslmode=require";
				return DriverManager.getConnection(dbURL, dbUsername, dbPassword);
			} catch (SQLException ex) {
				System.out.println("Failed to create the database connection.");
			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver not found.");
		}
		return null;
	}

}

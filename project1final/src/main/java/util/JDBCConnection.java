package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
	
	public static Connection conn = null;
	
	public static Connection getConnection() {
		String driver = "oracle.jdbc.driver.OracleDriver";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(conn == null) {
			String endpoint = "jdbc:oracle:thin:@csong.csasln9s54wq.us-east-2.rds.amazonaws.com:1521:ORCL";
			String username = "Admin";
			String password = "password";
			try {
				conn = DriverManager.getConnection(endpoint, username, password);
				return conn;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
}

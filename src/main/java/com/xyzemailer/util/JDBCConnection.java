package com.xyzemailer.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

	private static final String URL = "jdbc:postgresql://localhost:5432/xyzemailer";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "Chanith";
	private static final String DB_DRIVER = "org.postgresql.Driver";
	
	public static Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName(DB_DRIVER);
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
		} catch (ClassNotFoundException e) {
            System.err.println("❌ JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
            
        } catch (SQLException e) {
            System.err.println("❌ SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

        return connection;
	}
}

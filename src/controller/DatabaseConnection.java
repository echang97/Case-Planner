package controller;

import java.sql.*;


public class DatabaseConnection {
	public static Connection conn = null;
	public static Connection getConnection() {
		try {
			if (conn == null) {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection("jdbc:sqlite:casePlanner.sqlite");
				System.out.println("Database connected.");
			}
			return conn;
		} catch (Exception e) {
			System.out.println(e);
			return null;
			// TODO: handle exception
		}
	}
}

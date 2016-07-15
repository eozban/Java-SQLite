package com.esintibilisim.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connector_SQLite {
	private Connection conn;

	public Connector_SQLite(String databaseName) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s", databaseName));
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}

	public int execSql(String sql) {
		int effectedRows = -1;
		try {
			Statement stmt = conn.createStatement();
			effectedRows = stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Query EXEC: succesfull");
		return effectedRows;
	}
	
	public ResultSet viewSql(String sql) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery( sql );
			//stmt.close();
			//rs.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Query VIEW: succesfull");
		return rs;
	}

}
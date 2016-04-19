package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {
	protected static final String USR_LOGIN = "admincdb";
	protected static final String PSSWD_LOGIN = "qwerty1234";
	protected static final String BD_ADDR = "jdbc:mysql://127.0.0.1:3306/computer-database-db";
	protected static final String DB_DRIVER = "com.mysql.jdbc.Driver";

	protected static Connection connection;
	private static DBConnect _instance = null;

	public static DBConnect getInstance() {
		if (_instance == null) {
			_instance = new DBConnect();
		}
		return _instance;
	}

	private DBConnect() {
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot load class !");
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String query) {
		ResultSet res = null;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public int executeUpdate(String query) {
		int res = 0;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			res = statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public void openConnection() {
		try {
			connection = DriverManager.getConnection(BD_ADDR, USR_LOGIN, PSSWD_LOGIN);
		} catch (SQLException e) {
			System.out.println("Can't get connection from driver !");
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Can't close connection !");
			e.printStackTrace();
		}
	}

	protected void finalize() {
		try {
			if (!connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

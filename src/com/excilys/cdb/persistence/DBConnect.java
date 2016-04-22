package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Database connection class
 * Handle Query and update
 */
public class DBConnect {
	protected static final String USR_LOGIN = "admincdb";
	protected static final String PSSWD_LOGIN = "qwerty1234";
	protected static final String BD_ADDR = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	protected static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	final static Logger logger = LoggerFactory.getLogger(DBConnect.class);

	protected Connection connection;
	private static DBConnect _instance = null;

	/**
	 * get instance of DBConnect
	 * @return DBConnect instance
	 */
	public static DBConnect getInstance() {
		if (_instance == null) {
			_instance = new DBConnect();
		}
		return _instance;
	}

	/**
	 * BDConnect constructor
	 */
	private DBConnect() {
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			logger.warn("Cannot load class !");
			System.out.println("Cannot load class !");
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return this.connection;
	}

	/**
	 * open database connection
	 */
	public void openConnection() {
		try {
			connection = DriverManager.getConnection(BD_ADDR, USR_LOGIN, PSSWD_LOGIN);
		} catch (SQLException e) {
			System.out.println("Can't get connection from driver !");
			e.printStackTrace();
		}
	}

	/**
	 * close database connection
	 */
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Can't close connection !");
			e.printStackTrace();
		}
	}

	/**
	 * finalize : call database connection if connection is still open
	 */
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

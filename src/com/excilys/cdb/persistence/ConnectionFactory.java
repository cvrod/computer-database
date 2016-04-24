package com.excilys.cdb.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Database connection class
 * Handle Query and update
 */
public class ConnectionFactory {
	private static final String PROPERTIES_FILE = "ressources/mysql.properties";
	
	protected static String usrLogin;
	protected static String psswrdLogin;
	protected static String DBAddress;
	protected static String DBDriver;
	final static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

	protected Connection connection;
	private static ConnectionFactory _instance = null;

	/**
	 * get instance of DBConnect
	 * @return DBConnect instance
	 */
	public static ConnectionFactory getInstance() {
		if (_instance == null) {
			_instance = new ConnectionFactory();
		}
		return _instance;
	}

	/**
	 * BDConnect constructor
	 */
	private ConnectionFactory() {
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(PROPERTIES_FILE));
			
			usrLogin = properties.getProperty("USR_LOGIN");
			psswrdLogin = properties.getProperty("PSSWD_LOGIN");
			DBAddress = properties.getProperty("DB_ADDR");
			DBDriver = properties.getProperty("DB_DRIVER");
			Class.forName(DBDriver);
		} catch (ClassNotFoundException | IOException e) {
			logger.warn("Cannot connect to DB !");
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
			connection = DriverManager.getConnection(DBAddress, usrLogin, psswrdLogin);
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

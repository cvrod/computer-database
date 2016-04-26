package com.excilys.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**.
 * Database connection class Handle Query and update
 */
public class ConnectionFactory {
    private static final String PROPERTIES_FILE = "mysql.properties";

    protected static String usrLogin;
    protected static String psswrdLogin;
    protected static String dbAddress;
    protected static String dbDriver;
    static final Logger LOGGER = LoggerFactory
            .getLogger(ConnectionFactory.class);

    protected Connection connection;
    private static ConnectionFactory instance = null;

    /**.
     * get instance of DBConnect
     *
     * @return DBConnect instance
     */
    public static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    /**.
     * BDConnect constructor
     */
    private ConnectionFactory() {
        try {
            InputStream inputStream;
            inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
            Properties properties = new Properties();
            properties.load(inputStream);

            usrLogin = properties.getProperty("USR_LOGIN");
            psswrdLogin = properties.getProperty("PSSWD_LOGIN");
            dbAddress = properties.getProperty("DB_ADDR");
            dbDriver = properties.getProperty("DB_DRIVER");
            Class.forName(dbDriver);
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.warn("Cannot connect to DB !");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    /**.
     * open database connection
     */
    public void openConnection() {
        try {
            connection = DriverManager.getConnection(dbAddress, usrLogin,
                    psswrdLogin);
        } catch (SQLException e) {
            System.out.println("Can't get connection from driver !");
            e.printStackTrace();
        }
    }

    /**.
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

    /**.
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

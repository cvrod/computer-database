package com.excilys.cdb.persistence;

import java.io.Closeable;
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

    private static ConnectionFactory instance = null;
    
    static{
        instance = new ConnectionFactory();
    }

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
            LOGGER.error("Cannot connect to DB !");
            throw new ConnectionFactoryException(e);
        }
    }

    /**.
     * open database connection
     */
    public Connection openConnection() {
        try {
            return DriverManager.getConnection(dbAddress, usrLogin,
                    psswrdLogin);
        } catch (SQLException e) {
            LOGGER.error("Can't get connection from driver !");
            throw new ConnectionFactoryException(e);
        }
    }
    
    /**
     * close object 
     * @param object object to close
     */
    public void closeObject(Closeable object){
        try {
            object.close();
        } catch (IOException e) {
            LOGGER.error("Can't close object");
            throw new ConnectionFactoryException(e);
        }
    }
}

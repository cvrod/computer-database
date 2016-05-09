package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    ConnectionFactory connectionFactory = null;
    private static ConnectionManager instance;
    public static final ThreadLocal<Connection> THREAD_CONNECTION = new ThreadLocal<Connection>();

    /**.
     * Getting ConnectionManager instance
     * @return ConnectionManager
     */
    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    /**
     * . default constructor
     */
    private ConnectionManager() {
        connectionFactory = ConnectionFactory.getInstance();
    }

    /**.
     * Connection initialisation in ThreadLocal
     */
    public void init() {
        if (THREAD_CONNECTION.get() != null) {
            throw new ConnectionManagerException("Connection is already open");
        }

        Connection con = this.connectionFactory.openConnection();

        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            throw new ConnectionManagerException("AutoCommit problem !");
        }
        THREAD_CONNECTION.set(con);
    }

    /**.
     * Commit ThreadLocal Connection
     * @throws SQLException if commit fail
     */
    public void commit() throws SQLException {
        Connection con = THREAD_CONNECTION.get();
        con.commit();
    }

    /**.
     * Rollback ThreadLocal Connection
     */
    public void rollback() {
        Connection con = THREAD_CONNECTION.get();
        try {
            con.rollback();
        } catch (SQLException e) {
            throw new ConnectionManagerException("rollback problem !");
        }
    }

    /**.
     * Close connection and remove it from ThreadLocal
     */
    public void close() {
        Connection con = THREAD_CONNECTION.get();
        try {
            con.close();
        } catch (SQLException e) {
            throw new ConnectionManagerException("Could not close connection.");
        } finally {
            THREAD_CONNECTION.remove();
        }
    }

    /**.
     * return connection stored in ThreadLocal
     * @return Connection object
     */
    public Connection get() {
        Connection con = THREAD_CONNECTION.get();
        if (THREAD_CONNECTION.get() == null) {
            throw new ConnectionManagerException("Connection is null !");
        }
        return con;
    }
}

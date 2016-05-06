package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    ConnectionFactory connectionFactory = null;
    private static ConnectionManager instance;
    public static final ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();
    
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

    public void init() {
        if (threadConnection.get() != null) {
            throw new ConnectionManagerException("Connection is already open");
        }

        Connection con = this.connectionFactory.openConnection();

        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            throw new ConnectionManagerException("AutoCommit problem !");
        }
        threadConnection.set(con);
    }

    public void commit() throws SQLException {
        Connection con = threadConnection.get();
        con.commit();
    }

    public void rollback() {
        Connection con = threadConnection.get();
        try {
            con.rollback();
        } catch (SQLException e) {
            throw new ConnectionManagerException("rollback problem !");
        }
    }

    public void close() {
        Connection con = threadConnection.get();
        try {
            con.close();
        } catch (SQLException e) {
            throw new ConnectionManagerException("Could not close connection.");
        } finally {
            threadConnection.remove();
        }
    }

    public Connection get() {
        Connection con = threadConnection.get();
        if (threadConnection.get() == null) {
            throw new ConnectionManagerException("Connection is null !");
        }
        return con;
    }
}

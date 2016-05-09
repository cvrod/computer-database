package com.excilys.cdb.persistence;

/**
 * DAOException throw by ConnectionFactory.
 *
 */
public class ConnectionFactoryException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * ConnectionFactoryException default constructor.
     * @param message message to return
     */
    public ConnectionFactoryException(String message) {
        super(message);
    }

    /**
     * ConnectionFactoryException constructor.
     * @param message message to return
     * @param cause cause to Throw
     */
    public ConnectionFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * ConnectionFactoryException constructor.
     * @param cause cause to throw
     */
    public ConnectionFactoryException(Throwable cause) {
        super(cause);
    }
}


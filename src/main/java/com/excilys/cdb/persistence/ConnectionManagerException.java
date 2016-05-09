package com.excilys.cdb.persistence;

public class ConnectionManagerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * ConnectionManagerException default constructor.
     * @param message message to return
     */
    public ConnectionManagerException(String message) {
        super(message);
    }

    /**
     * ConnectionManagerException constructor.
     * @param message message to return
     * @param cause cause to Throw
     */
    public ConnectionManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * ConnectionManagerException constructor.
     * @param cause cause to throw
     */
    public ConnectionManagerException(Throwable cause) {
        super(cause);
    }
}

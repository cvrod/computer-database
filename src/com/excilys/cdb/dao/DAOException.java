package com.excilys.cdb.dao;

/**
 * DAOException throw by ComputerDAO & CompanyDAO class
 *
 */
public class DAOException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public DAOException(String message) {
		super(message);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}
}

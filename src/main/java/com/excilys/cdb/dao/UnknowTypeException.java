package com.excilys.cdb.dao;

/**
 * Exception throw when the wrong table is given to DAO.
 *
 */
public class UnknowTypeException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Exception constructor.
     */
    public UnknowTypeException() {
        System.out.println("Unknow Type !");
    }

}

package com.excilys.cdb.dao;

/**.
 * DAOException throw by ComputerDAO & CompanyDAO class
 *
 */
public class DAOException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**.
     * DAOException default constructor
     * @param message message to return
     */
    public DAOException(String message) {
        super(message);
    }

    /**.
     * DAOException constructor
     * @param message message to return
     * @param cause cause to Throw
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**.
     * DAOException constructor
     * @param cause cause to throw
     */
    public DAOException(Throwable cause) {
        super(cause);
    }
}

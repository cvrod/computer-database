package com.excilys.cdb.validator;

/**.
 * ValidatorException throw by CompanyValidator & ComputerValidator
 *
 */
public class ValidatorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**.
     * ValidatorException default constructor
     * @param message message to return
     */
    public ValidatorException(String message) {
        super(message);
    }

    /**.
     * ValidatorException constructor
     * @param message message to return
     * @param cause cause to Throw
     */
    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**.
     * ValidatorException constructor
     * @param cause cause to throw
     */
    public ValidatorException(Throwable cause) {
        super(cause);
    }
}

package com.excilys.cdb.mapper;

/**.
 * MapperException throw by ConnectionFactory
 *
 */
public class MapperException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**.
     * MapperException default constructor
     * @param message message to return
     */
    public MapperException(String message) {
        super(message);
    }

    /**.
     * MapperException constructor
     * @param message message to return
     * @param cause cause to Throw
     */
    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }

    /**.
     * MapperException constructor
     * @param cause cause to throw
     */
    public MapperException(Throwable cause) {
        super(cause);
    }
}

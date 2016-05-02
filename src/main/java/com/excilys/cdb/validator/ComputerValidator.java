package com.excilys.cdb.validator;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * . Computer Validator use to validate Computer fields
 *
 */
public class ComputerValidator {
    static final Logger LOGGER = LoggerFactory
            .getLogger(ComputerValidator.class);
    private final static Pattern INT_REGEX = Pattern
            .compile("[0-9]*[1-9][0-9]*");
    private final static Pattern DATE_REGEX = Pattern.compile(
            "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])");

    /**.
     * . validate Computer name
     *
     * @param name
     *            name to validate
     * @return boolean true if name OK, false else
     */
    public static boolean validateName(String name) {
        LOGGER.debug("validateName");
        return !(name == null || name.equals(""));
    }

    /**.
     * . validate Computer company id
     *
     * @param id
     *            company id to validate
     * @return boolean true if id OK, false else
     */
    public static boolean validateCompanyId(String id) {
        LOGGER.debug("validateCompanyId");
        return INT_REGEX.matcher(id).matches();
    }

    /**.
     * . validate a date from a string
     *
     * @param date
     *            str date to validate
     * @return true is date OK, false else
     */
    public static boolean validateDate(String date) {
        LOGGER.debug("validateDate");
        return DATE_REGEX.matcher(date).matches();
    }
}

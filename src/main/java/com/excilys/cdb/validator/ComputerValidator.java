package com.excilys.cdb.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**.
 * Computer Validator use to validate Computer fields
 *
 */
public class ComputerValidator {
    static final Logger LOGGER = LoggerFactory
            .getLogger(ComputerValidator.class);

    /**.
     * validate Computer name
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
     * validate Computer company id
     *
     * @param id
     *            company id to validate
     * @return boolean true if id OK, false else
     */
    public static boolean validateCompanyId(String id) {
        LOGGER.debug("validateCompanyId");
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**.
     * validate a date from a string
     *
     * @param date
     *            str date to validate
     * @return true is date OK, false else
     */
    public static boolean validateDate(String date) {
        LOGGER.debug("validateDate");
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

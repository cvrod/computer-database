package com.excilys.cdb.validator;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Computer;

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
     */
    public static void validateName(String name) {
        LOGGER.debug("validateName");
        if(name == null || name.equals("")){
            throw new ValidatorException("Computer name cant be null or \"\"");
        }
    }

    /**.
     * . validate Computer company id
     *
     * @param id
     *            company id to validate
     */
    public static void validateCompanyId(String id) {
        LOGGER.debug("validateCompanyId");
        if (!INT_REGEX.matcher(id).matches() && !id.equals("0")){
            throw new ValidatorException("Computer company id is invalid");
        }
    }

    /**.
     * . validate a date from a string
     *
     * @param date
     *            str date to validate
     */
    public static void validateDate(String date) {
        LOGGER.debug("validateDate");
        if (!DATE_REGEX.matcher(date).matches() && !date.equals("")){
            throw new ValidatorException("Date is invalid");
        }
    }
    
    public static void validate(Computer c){
        validateName(c.getName());
        validateCompanyId(c.getCompany().getId().toString());
        validateDate(c.getIntroduced().toString());
        validateDate(c.getDiscontinued().toString());
    }
}

package com.excilys.cdb.validator;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Computer;

/**
 * Computer Validator use to validate Computer fields.
 *
 */
public class ComputerValidator {
    static final Logger LOGGER = LoggerFactory
            .getLogger(ComputerValidator.class);
    private static final Pattern INT_REGEX = Pattern
            .compile("[0-9]*[1-9][0-9]*");
    private static final Pattern DATE_REGEX = Pattern.compile(
            "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])");

    /**
     * validate Computer name.
     *
     * @param name
     *            name to validate
     */
    public static void validateName(String name) {
        LOGGER.debug("validateName");
        if (name == null || name.equals("")) {
            throw new ValidatorException("Computer name cant be null or \"\"");
        }
    }

    /**
     * validate Computer id or company id.
     *
     * @param id
     *            id to validate
     */
    public static void validateId(String id) {
        LOGGER.debug("validateCompanyId");
        if (!INT_REGEX.matcher(id).matches() && !id.equals("0")) {
            throw new ValidatorException("Computer company id is invalid");
        }
    }

    /**
     * validate a date from a string.
     *
     * @param date
     *            str date to validate
     */
    public static void validateDate(String date) {
        LOGGER.debug("validateDate");
        if (!DATE_REGEX.matcher(date).matches() && !date.equals("")) {
            throw new ValidatorException("Date is invalid");
        }
    }

    /**
     * Validate a Computer.
     * @param c computer to validate
     */
    public static void validate(Computer c) {
        validateName(c.getName());
        if(c.getCompany() != null){
            validateId(c.getCompany().getId().toString());
        }
        boolean introduced = false;
        if (c.getIntroduced() != null) {
            validateDate(c.getIntroduced().toString());
            introduced = true;
        }
        if (c.getDiscontinued() != null) {
            validateDate(c.getDiscontinued().toString());
            if (introduced) {
                if (!c.getDiscontinued().isAfter(c.getIntroduced())) {
                    throw new ValidatorException("Intro date is after discontinued date");
                }
            }
        }
    }
}

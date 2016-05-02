package com.excilys.cdb.validator;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;

/**
 * . Company Validator use to validate Company fields
 *
 */
public class CompanyValidator {
    static final Logger LOGGER = LoggerFactory
            .getLogger(CompanyValidator.class);
    private final static Pattern INT_REGEX = Pattern
            .compile("[0-9]*[1-9][0-9]*");

    /**
     * . validate Company name
     *
     * @param name
     *            name to validate
     */
    public static void validateName(String name) {
        LOGGER.debug("validateName");
        if (name == null || name.equals("")) {
            throw new ValidatorException("Company Name cant be null or \"\"");
        }
    }

    /**
     * . validate Company ID from a given String
     * 
     * @param id
     *            id to validate
     */
    public static void validateId(String id) {
        LOGGER.debug("validateId");
        if(!INT_REGEX.matcher(id).matches()){
            throw new ValidatorException("Company Id is invalid");
        }
    }
    
    public static void validate(Company c) {
        LOGGER.debug("validate()");
        validateName(c.getName());
        validateId(c.getId().toString());
    }
}

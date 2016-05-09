package com.excilys.cdb.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;

/**
 * Company Validator use to validate Company fields.
 *
 */
public class CompanyValidator {
    static final Logger LOGGER = LoggerFactory
            .getLogger(CompanyValidator.class);
    private static final Pattern INT_REGEX = Pattern
            .compile("[0-9]*[1-9][0-9]*");
    static List<Error> errorList = new ArrayList<>();

    /**
     * validate Company name.
     *
     * @param name
     *            name to validate
     */
    public static void validateName(String name) {
        LOGGER.debug("validateName");
        if (name == null || name.equals("")) {
            errorList.add(new Error("name", "Company Name cant be null or \"\""));
        }
    }

    /**
     * validate Company ID from a given String.
     *
     * @param id
     *            id to validate
     */
    public static void validateId(String id) {
        LOGGER.debug("validateId");
        if (!INT_REGEX.matcher(id).matches()) {
            errorList.add(new Error("id", "Company Id is invalid"));
        }
    }

    /**
     * Validate a whole company.
     * @param c company to validate
     * @return errorList
     */
    public static List<Error> validate(Company c) {
        LOGGER.debug("validate()");
        errorList = new ArrayList<>();
        validateName(c.getName());
        if (c.getId() != null) {
            validateId(c.getId().toString());
        }
        return errorList;
    }
}

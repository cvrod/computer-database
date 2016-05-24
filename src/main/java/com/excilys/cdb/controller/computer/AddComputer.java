package com.excilys.cdb.controller.computer;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerValidator;
import com.excilys.cdb.validator.ValidatorException;
import com.excilys.cdb.dao.DAOException;

/**
 * Add page controller.
 */
@Controller
@RequestMapping("/add")
public class AddComputer {
    @Autowired
    @Qualifier("computerService")
    ComputerService computerService;
    @Autowired
    @Qualifier("companyService")
    CompanyService companyService = null;
    static final Logger LOGGER = LoggerFactory.getLogger(AddComputer.class);
    ArrayList<Company> companies = null;

    /**
     * adding computer form.
     * @param model ModelMap object use to transmit attribute
     * @return controller name
     */
    @RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
    protected String doGet(ModelMap model) {
        try {
            companies = (ArrayList<Company>) companyService.listAll();
            LOGGER.debug("Getting CompanyService.listAll()");
        } catch (DAOException e) {
            LOGGER.debug("Can't get companies list !");
            companies = new ArrayList<>();
        }
        model.addAttribute("companies", companies);
        return "addComputer";
    }

    /**
     * Processing adding computer request.
     * @param model ModelMap object use to transmit attribute
     * @param nameParam computer name
     * @param introducedParam introduced date
     * @param discontinuedParam discontinued date
     * @param companyIdParam company id
     * @return controller name redirection
     */
    @RequestMapping(value = { "/", "" }, method = RequestMethod.POST)
    protected String doPost(ModelMap model,
            @RequestParam(value = "computerName") String nameParam,
            @RequestParam(value = "introduced") String introducedParam,
            @RequestParam(value = "discontinued") String discontinuedParam,
            @RequestParam(value = "companyId") String companyIdParam) {
        LOGGER.debug("nameParam : " + nameParam);
        LOGGER.debug("introducedParam : " + introducedParam);
        LOGGER.debug("discontinuedParam : " + discontinuedParam);
        LOGGER.debug("companyIdParam : " + companyIdParam);
        int companyID = 0;

        try {
            ComputerValidator.validateName(nameParam);
            ComputerValidator.validateDate(introducedParam);
            ComputerValidator.validateDate(discontinuedParam);
            ComputerValidator.validateId(companyIdParam);

            companyID = Integer.parseInt(companyIdParam);
            if (introducedParam.equals("")) {
                LOGGER.debug("Invalid or null introduction date... Skipping");
                introducedParam = null;
            }
            if (discontinuedParam.equals("")) {
                LOGGER.debug("Invalid or null discontinued date... Skipping");
                discontinuedParam = null;
            }
            try {
                Company c = null;
                if (companyID != 0) {
                    c = companyService.get(companyID);
                }

                Computer comp = new Computer.Builder().name(nameParam)
                        .introduced(introducedParam)
                        .discontinued(discontinuedParam).company(c).build();
                computerService.add(comp);
                return "redirect:computer";

            } catch (DAOException e) {
                return "500";
            }

        } catch (ValidatorException e) {
            model.addAttribute("companies", companies);
            return "redirect:add";
        }
    }
}

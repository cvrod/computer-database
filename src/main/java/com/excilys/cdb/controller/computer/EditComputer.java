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

import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.dto.model.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerValidator;
import com.excilys.cdb.validator.ValidatorException;

/**
 * Edit Page Controller.
 */
@Controller
@RequestMapping("/edit")
public class EditComputer {
    @Autowired
    @Qualifier("computerService")
    ComputerService computerService;
    @Autowired
    @Qualifier("companyService")
    CompanyService companyService;
    static final Logger LOGGER = LoggerFactory.getLogger(EditComputer.class);
    ArrayList<Company> companies = null;
    ComputerDTO computerDTO = null;

    /**
     * edit form request.
     * @param model use to transmit attribute to the view
     * @param paramId id url param
     * @return controller name redirection
     */
    @RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
    protected String doGet(ModelMap model,
            @RequestParam(value = "id") String paramId) {

        int id = 0;
        LOGGER.debug("Try to retrieve Computer of id(str) : " + paramId);
        Computer computer = null;

        if (paramId != null) {
            try {
                id = Integer.parseInt(paramId);
                LOGGER.info("getting Id : " + id);
            } catch (NumberFormatException e) {
                LOGGER.debug("NumberFormatException on id param !");
            }
        }
        try {
            companies = (ArrayList<Company>) companyService.listAll();
            computer = computerService.get(id);
            LOGGER.debug("getting computer : " + computer.toString());
            computerDTO = new ComputerDTO(computer);
            LOGGER.debug("Getting CompanyService.listAll()");

        } catch (DAOException e) {
            LOGGER.debug("Can't get companies list ! or computer");
            companies = new ArrayList<>();
        }

        model.addAttribute("companies", companies);
        model.addAttribute("id", id);
        model.addAttribute("computer", computerDTO);

        return "editComputer";
    }

    /**
     * processing edit request.
     * @param model model use to transmit attribute to the view
     * @param nameParam computer name url param
     * @param introducedParam introduced date url param
     * @param discontinuedParam discontinued date url param
     * @param companyIdParam company id url param
     * @param idParam computer id url param
     * @return controller name redirection
     */
    @RequestMapping(value = { "/", "" }, method = RequestMethod.POST)
    protected String doPost(ModelMap model,
            @RequestParam(value = "computerName") String nameParam,
            @RequestParam(value = "introduced") String introducedParam,
            @RequestParam(value = "discontinued") String discontinuedParam,
            @RequestParam(value = "companyId") String companyIdParam,
            @RequestParam(value = "id") String idParam) {
        LOGGER.debug("Trying to update computer " + idParam + " with : ");
        LOGGER.debug("nameParam : " + nameParam);
        LOGGER.debug("introducedParam : " + introducedParam);
        LOGGER.debug("discontinuedParam : " + discontinuedParam);
        LOGGER.debug("companyIdParam : " + companyIdParam);
        int id = Integer.parseInt(idParam);
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
                computerService.update(id, comp);
                return "redirect:computer";

            } catch (DAOException e) {
                return "500";
            }

        } catch (ValidatorException e) {
            model.addAttribute("id", id);
            model.addAttribute("computer", computerDTO);
            model.addAttribute("companies", companies);
            return "redirect:edit";
        }
    }
}

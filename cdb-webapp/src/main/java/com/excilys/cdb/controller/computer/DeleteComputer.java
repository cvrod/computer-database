package com.excilys.cdb.controller.computer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class DeleteComputer.
 */
@Controller
@RequestMapping("/delete")
public class DeleteComputer {
    @Autowired
    @Qualifier("computerService")
    private ComputerService computerService = null;
    static final Logger LOGGER = LoggerFactory.getLogger(DeleteComputer.class);

    /**
     * Processing deleting computer request.
     * @param model use to transmit attributes
     * @param selection id's of computer candidates for deletion
     * @return controller name redirection
     */
    @RequestMapping(value = { "/", "" }, method = RequestMethod.POST)
    protected String doPost(ModelMap model,
            @RequestParam(value = "selection") String selection) {
        LOGGER.debug("received : " + selection);
        String[] selectionSplit = null;

        if (selection != null) {
            selectionSplit = selection.split(",");
        }
        for (String s : selectionSplit) {
            computerService.delete(Integer.parseInt(s));
        }
        return "redirect:computer";
    }
}

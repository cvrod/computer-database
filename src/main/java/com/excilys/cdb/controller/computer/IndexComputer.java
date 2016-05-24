package com.excilys.cdb.controller.computer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.util.PageParameter;

/**
 * Dashboard Controller.
 */
@Controller
@RequestMapping("/computer")
public class IndexComputer {

    // wrapper initialisation
    @Autowired
    PageParameter pageParam;

    static final Logger LOGGER = LoggerFactory.getLogger(IndexComputer.class);

    /**
     * return a computer page.
     *
     * @param request
     *            request object
     * @param response
     *            response object
     * @throws IOException
     *             IOException
     */
    @RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
    protected String doGet(
            ModelMap model,
            @RequestParam(value = "offset", defaultValue = "10") String paramOffset,
            @RequestParam(value = "page", defaultValue = "0") String paramPage,
            @RequestParam(value = "search", defaultValue = "") String paramSearch,
            @RequestParam(value = "order", defaultValue = "id") String paramOrder,
            @RequestParam(value = "dir", defaultValue = "asc") String paramDirection) {

        pageParam.setOffset(paramOffset);
        pageParam.setCurrentPage(paramPage);
        pageParam.setSearch(paramSearch);
        pageParam.setOrder(paramOrder);
        pageParam.setDir(paramDirection);

        // process paramater to create computerDTO page
        pageParam.process();

        // Setting jsp attributes
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("nbPages",
                (int) Math.ceil((double) pageParam.getCountComputer()
                        / (double) pageParam.getOffset()));

        // Forward request
        return "indexComputer";
    }
}

package com.excilys.cdb.controller.computer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.dao.DAOException;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet(name = "AddComputer", urlPatterns = { "/computer/add" })
public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ComputerService computerService = null;
    CompanyService companyService = null;
    static final Logger LOGGER = LoggerFactory.getLogger(AddComputer.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
        computerService = ComputerService.getInstance();
        companyService = CompanyService.getInstance();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Company> companies = null;
        try {
            companies = companyService.listAll();
            LOGGER.debug("Getting CompanyService.listAll()");
        } catch (DAOException e) {
            LOGGER.debug("Can't get companies list !");
            companies = new ArrayList<>();
        }
        request.setAttribute("companies", companies);
        request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp")
                .forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String nameParam = request.getParameter("computerName");
        LOGGER.debug("nameParam : " + nameParam);
        String introducedParam = request.getParameter("introduced");
        LOGGER.debug("introducedParam : " + introducedParam);
        String discontinuedParam = request.getParameter("discontinued");
        LOGGER.debug("discontinuedParam : " + discontinuedParam);
        String companyIdParam = request.getParameter("companyId");
        LOGGER.debug("companyIdParam : " + companyIdParam);
        int companyID = 0;

        if ((nameParam == null) || nameParam.equals("")) {
            request.setAttribute("computerName", nameParam);
            request.setAttribute("introduced", introducedParam);
            request.setAttribute("discontinued", discontinuedParam);
            request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp")
                    .forward(request, response);

        } else {
            try {
                companyID = Integer.parseInt(companyIdParam);
            } catch (NumberFormatException e) {
                LOGGER.debug("Invalid Company ID... Skipping");
            }
            try {
                LocalDate.parse(introducedParam);
            } catch (DateTimeParseException e) {
                LOGGER.debug("Invalid or null introduction date... Skipping");
                introducedParam = null;
            }
            try {
                LocalDate.parse(discontinuedParam);
            } catch (DateTimeParseException e) {
                LOGGER.debug("Invalid or null discontinued date... Skipping");
                discontinuedParam = null;
            }
            try {
                Company c;
                c = companyService.get(companyID);

                Computer comp = new Computer.Builder().name(nameParam)
                        .introduced(introducedParam)
                        .discontinued(discontinuedParam).company(c).build();
                computerService.add(comp);
                response.sendRedirect(request.getContextPath() + "/computer");

            } catch (final DAOException e) {
                request.getRequestDispatcher("/WEB-INF/views/500.html")
                        .forward(request, response);
            }
        }
    }

}

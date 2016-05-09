package com.excilys.cdb.controller.computer;

import java.io.IOException;
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
import com.excilys.cdb.service.CompanyServiceImpl;
import com.excilys.cdb.service.ComputerServiceImpl;
import com.excilys.cdb.validator.ComputerValidator;
import com.excilys.cdb.validator.ValidatorException;
import com.excilys.cdb.dao.DAOException;

/**
 * Servlet implementation class AddComputer.
 */
@WebServlet(name = "AddComputer", urlPatterns = { "/computer/add" })
public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ComputerServiceImpl computerService = null;
    CompanyServiceImpl companyService = null;
    static final Logger LOGGER = LoggerFactory.getLogger(AddComputer.class);
    ArrayList<Company> companies = null;

    /**
     * AddComputer Servlet constructor.
     */
    public AddComputer() {
        super();
        computerService = ComputerServiceImpl.getInstance();
        companyService = CompanyServiceImpl.getInstance();
    }

    /**
     * return addcomputer form.
     * @param request request object
     * @param response response object
     *
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            companies = (ArrayList<Company>) companyService.listAll();
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
     * add a computer to DB.
     * @param request request object
     * @param response response object
     *
     * @throws ServletException Servlet Exception
     * @throws IOException IOException
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
                Company c;
                c = companyService.get(companyID);

                Computer comp = new Computer.Builder().name(nameParam)
                        .introduced(introducedParam)
                        .discontinued(discontinuedParam).company(c).build();
                computerService.add(comp);
                response.sendRedirect(request.getContextPath() + "/computer");

            } catch (DAOException e) {
                request.getRequestDispatcher("/WEB-INF/views/500.html")
                        .forward(request, response);
            }

        } catch (ValidatorException e) {
            request.setAttribute("companies", companies);
            request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp")
                    .forward(request, response);
        }
    }
}

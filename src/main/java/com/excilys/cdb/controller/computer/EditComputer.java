package com.excilys.cdb.controller.computer;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.dto.model.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyServiceImpl;
import com.excilys.cdb.service.ComputerServiceImpl;
import com.excilys.cdb.validator.ComputerValidator;
import com.excilys.cdb.validator.ValidatorException;

/**
 * Servlet implementation class EditComputer.
 */
@WebServlet(name = "EditComputer", urlPatterns = { "/computer/edit" })
public class EditComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    @Qualifier("computerService")
    ComputerServiceImpl computerService;
    @Autowired
    @Qualifier("companyService")
    CompanyServiceImpl companyService;
    static final Logger LOGGER = LoggerFactory.getLogger(EditComputer.class);
    ArrayList<Company> companies = null;
    ComputerDTO computerDTO = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    /**
     * doGet.
     * 
     * @param request
     *            request object
     * @param response
     *            response object
     *
     * @throws ServletException
     *             Servlet Exception
     * @throws IOException
     *             IOException
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String paramId = request.getParameter("id");
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

        request.setAttribute("companies", companies);
        request.setAttribute("id", id);
        request.setAttribute("computer", computerDTO);

        request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp")
                .forward(request, response);
    }

    /**
     * . doPost.
     * 
     * @param request
     *            request object
     * @param response
     *            response object
     *
     * @throws ServletException
     *             Servlet Exception
     * @throws IOException
     *             IOException
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
        int id = Integer.parseInt(request.getParameter("id"));
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
                computerService.update(id, comp);
                response.sendRedirect(request.getContextPath() + "/computer");

            } catch (DAOException e) {
                request.getRequestDispatcher("/WEB-INF/views/500.html")
                        .forward(request, response);
            }

        } catch (ValidatorException e) {
            request.setAttribute("id", id);
            request.setAttribute("computer", computerDTO);
            request.setAttribute("companies", companies);
            request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp")
                    .forward(request, response);
        }
    }
}

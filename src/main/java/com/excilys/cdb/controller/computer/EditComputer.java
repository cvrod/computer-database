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

import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.dto.model.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

/**.
 * . Servlet implementation class EditComputer
 */
@WebServlet(name = "EditComputer", urlPatterns = { "/computer/edit" })
public class EditComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ComputerService computerService = null;
    CompanyService companyService = null;
    static final Logger LOGGER = LoggerFactory.getLogger(EditComputer.class);
    ArrayList<Company> companies = null;
    
    /**.
     * EditComputer Servlet constructor
     */
    public EditComputer() {
        super();
        computerService = ComputerService.getInstance();
        companyService = CompanyService.getInstance();
    }

    /**.
     * doGet
     * @param request request object
     * @param response response object
     *
     * @throws ServletException Servlet Exception
     * @throws IOException IOException
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String paramId = request.getParameter("id");
        int id = 0;
        LOGGER.debug("Try to retrieve Computer of id(str) : "+paramId);
        ComputerDTO computerDTO = null;
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
            companies = companyService.listAll();
            computer = computerService.get(id);
            LOGGER.debug("getting computer : "+computer.toString());
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

    /**.
     * doPost
     * @param request request object
     * @param response response object
     *
     * @throws ServletException Servlet Exception
     * @throws IOException IOException
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

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

import com.excilys.cdb.dto.model.ComputerDTO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.pagination.Page;
import com.excilys.cdb.service.ComputerService;

/**
 * . . Servlet implementation class IndexComputer
 */
@WebServlet(name = "IndexComputer", urlPatterns = { "/computer" })
public class IndexComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static ComputerService computerService = null;
    static int offset = 10;
    static int currentPage = 0;
    static int nbPages;
    static final Logger LOGGER = LoggerFactory.getLogger(IndexComputer.class);

    /**
     * . IndexComputer Servlet constructor
     */
    public IndexComputer() {
        super();
        computerService = ComputerService.getInstance();
    }

    /**
     * . return a computer page
     *
     * @param request
     *            request object
     * @param response
     *            response object
     *
     * @throws ServletException
     *             ServletException
     * @throws IOException
     *             IOException
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String paramOffset = request.getParameter("offset");
        String paramPage = request.getParameter("page");
        String paramSearch = request.getParameter("search");
        String paramOrder = request.getParameter("order");
        if (paramOffset != null) {
            try {
                offset = Integer.parseInt(paramOffset);
                LOGGER.info("getting offset : " + offset);
            } catch (NumberFormatException e) {
                LOGGER.debug("NumberFormatException on index param !");
                offset = 10;
            }
        }
        if (paramPage != null) {
            try {
                currentPage = Integer.parseInt(paramPage);
                LOGGER.info("getting current page : " + currentPage);
            } catch (NumberFormatException e) {
                LOGGER.debug("NumberFormatException on page param !");
                currentPage = 0;
            }
        }

        Long countComputer = new Long(0);
        Page<Computer> computerPage = null;

        if (paramSearch == null && paramOrder == null) {
            computerPage = computerService.listAllByPage(currentPage * offset,
                    offset);
            countComputer = computerService.count();
        } else if (paramOrder == null) {
            computerPage = computerService.listByPage(paramSearch.trim(), "id",
                    currentPage * offset, offset);
            countComputer = computerService.count(paramSearch.trim());
        } else {
            computerPage = computerService.listByPage(paramSearch.trim(),
                    paramOrder, currentPage * offset, offset);
            countComputer = computerService.count(paramSearch.trim());
        }

        ArrayList<ComputerDTO> computerDtoArray = new ArrayList<>();
        ComputerDTO dtoTmp = null;
        for (Computer c : computerPage.getElementList()) {
            dtoTmp = new ComputerDTO(c);
            computerDtoArray.add(dtoTmp);
        }

        Page<ComputerDTO> computerDtoPage = new Page<>(computerDtoArray,
                computerPage.getStart(), computerPage.getOffset());

        request.setAttribute("page", computerDtoPage);
        request.setAttribute("current", currentPage);
        request.setAttribute("countComputer", countComputer);
        request.setAttribute("offset", offset);
        request.setAttribute("search", paramSearch);
        request.setAttribute("nbPages",
                (int) Math.ceil((double) countComputer / (double) offset));
        request.setAttribute("order", paramOrder);

        request.getRequestDispatcher("/WEB-INF/views/indexComputer.jsp")
                .forward(request, response);
    }

    /**
     * . doPost
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
        doGet(request, response);
    }
}

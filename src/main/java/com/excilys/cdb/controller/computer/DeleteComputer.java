package com.excilys.cdb.controller.computer;

import java.io.IOException;

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

import com.excilys.cdb.service.ComputerServiceImpl;

/**
 * Servlet implementation class DeleteComputer.
 */
@WebServlet(name = "DeleteComputer", urlPatterns = { "/computer/delete" })
public class DeleteComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    @Qualifier("computerService")
    private ComputerServiceImpl computerService = null;
    static final Logger LOGGER = LoggerFactory.getLogger(DeleteComputer.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
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
        response.getWriter().append("Served at: ")
                .append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        final String selection = request.getParameter("selection");
        LOGGER.debug("received : " + selection);
        String[] selectionSplit = null;

        if (selection != null) {
            selectionSplit = selection.split(",");
        }
        for (String s : selectionSplit) {
            computerService.delete(Integer.parseInt(s));
        }
        response.sendRedirect(request.getContextPath() + "/computer");
    }
}

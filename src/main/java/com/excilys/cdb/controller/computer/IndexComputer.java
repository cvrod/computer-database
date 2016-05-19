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
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb.util.PageParameter;

/**
 * Servlet implementation class IndexComputer.
 */
@WebServlet(name = "IndexComputer", urlPatterns = { "/computer" })
public class IndexComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // wrapper initialisation
    @Autowired
    PageParameter pageParam;

    static final Logger LOGGER = LoggerFactory.getLogger(IndexComputer.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    /**
     * return a computer page.
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
        // Getting parameter from url
        String paramOffset = request.getParameter("offset");
        String paramPage = request.getParameter("page");
        String paramSearch = request.getParameter("search");
        String paramOrder = request.getParameter("order");
        String paramDirection = request.getParameter("dir");

        pageParam.setOffset(paramOffset);
        pageParam.setCurrentPage(paramPage);
        pageParam.setSearch(paramSearch);
        pageParam.setOrder(paramOrder);
        pageParam.setDir(paramDirection);

        // process paramater to create computerDTO page
        pageParam.process();

        // Setting jsp attributes
        request.setAttribute("pageParam", pageParam);
        request.setAttribute("nbPages",
                (int) Math.ceil((double) pageParam.getCountComputer()
                        / (double) pageParam.getOffset()));

        // Forward request
        request.getRequestDispatcher("/WEB-INF/views/indexComputer.jsp")
                .forward(request, response);
    }

    /**
     * doPost.
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

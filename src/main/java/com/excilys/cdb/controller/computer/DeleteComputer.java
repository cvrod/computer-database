package com.excilys.cdb.controller.computer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet(name = "DeleteComputer", urlPatterns = { "/computer/delete" })
public class DeleteComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService = null;
	static final Logger LOGGER = LoggerFactory.getLogger(DeleteComputer.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputer() {
        super();
        this.computerService = ComputerService.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    final String selection = request.getParameter("selection");
	    LOGGER.debug("received : " + selection);
	    String[] selectionSplit = null;

	    if(selection != null){
	         selectionSplit = selection.split(",");
	    }
	    for(String s : selectionSplit){
	        computerService.delete(Integer.parseInt(s));
	    }
	    response.sendRedirect(request.getContextPath() + "/computer");
	}
}

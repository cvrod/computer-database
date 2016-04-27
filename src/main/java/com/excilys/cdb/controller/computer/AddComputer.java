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
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.dao.DAOException;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet(name = "AddComputer", urlPatterns = {"/computer/add"})
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    ArrayList<Company> companies = null;
	    try{
	        companies = companyService.listAll();
	        LOGGER.debug("Getting CompanyService.listAll()");
	    }catch (DAOException e){
	        LOGGER.debug("Can't get companies list !");
	        companies = new ArrayList<>();
	    }
	    request.setAttribute("companies", companies);
	    request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

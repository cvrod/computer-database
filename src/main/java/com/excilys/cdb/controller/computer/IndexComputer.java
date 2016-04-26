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

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.pagination.Page;
import com.excilys.cdb.service.ComputerService;

/**.
 * Servlet implementation class IndexComputer
 */
@WebServlet(name = "IndexComputer",urlPatterns = {"/computer"})
public class IndexComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static ComputerService computerService = null;
	static int index = 0;
    public static final int PAGE_OFFSET = 20;
    static final Logger LOGGER = LoggerFactory.getLogger(IndexComputer.class);
       
    /**.
     * @see HttpServlet#HttpServlet()
     */
    public IndexComputer() {
        super();
        computerService = ComputerService.getInstance();
    }

	/**.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String paramIndex = request.getParameter("index");
	    if(paramIndex != null){
	        try{
	            index = Integer.parseInt(paramIndex);
	            LOGGER.info("getting index : " + index);
	        }catch(NumberFormatException e){
	            LOGGER.debug("NumberFormatException on index param !");
	            index = 0;
	        }
	    }
	    Page<Computer> computerPage = computerService.listAllByPage(index, PAGE_OFFSET);
	    ArrayList<Computer> computerList = computerService.listAll();
	    
	    request.setAttribute("page", computerPage);
	    request.setAttribute("index", index+=20);
	    request.setAttribute("countComputer", computerList.size());

	    request.getRequestDispatcher("/WEB-INF/views/indexComputer.jsp").forward(request, response);
	}

	/**.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

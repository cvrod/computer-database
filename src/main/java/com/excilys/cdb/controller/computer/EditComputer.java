package com.excilys.cdb.controller.computer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**.
 * . Servlet implementation class EditComputer
 */
@WebServlet(name = "EditComputer", urlPatterns = { "/computer/edit" })
public class EditComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**.
     * EditComputer Servlet constructor
     */
    public EditComputer() {
        super();
        // TODO Auto-generated constructor stub
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
        request.getRequestDispatcher("/WEB-INF/views/editComputer.html")
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
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}

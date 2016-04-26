package com.excilys.cdb.service;

import java.util.ArrayList;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.pagination.Page;

public class ComputerService {
    private ComputerDAO computerDAO = null;
    private static ComputerService computerService = null;

    /**.
     * ComputerService constructor
     */
    private ComputerService() {
        computerDAO = ComputerDAO.getInstance();
    }

    /**.
     * return instance of ComputerService singleton
     * @return instance of ComputerService
     */
    public static synchronized ComputerService getInstance() {
        if (computerService == null) {
            computerService = new ComputerService();
        }
        return computerService;
    }

    /**.
     * getting a Computer from database
     * @param id if of computer to get
     * @return Computer Object
     */
    public Computer get(int id) {
        return computerDAO.get(id);
    }

    /**.
     * add a computer to DB
     * @param comp computer to add
     * @return fresh Computer Object
     */
    public Computer add(Computer comp) {
        return computerDAO.add(comp);
    }

    /**.
     * remove a Computer from DB
     * @param id id of Computer to delete
     * @return 0 if computer not found, 1 else
     */
    public int delete(int id) {
        return computerDAO.delete(id);
    }

    /**.
     * update a Computer on DB
     * @param id id of computer to update
     * @param c Fresh Computer object
     * @return 0 if computer not found, 1 else
     */
    public int update(int id, Computer c) {
        return computerDAO.update(id, c);
    }
    /**.
     * listAll Computer
     * @return ArrayList of all computer in DB
     */
    public ArrayList<Computer> listAll() {
        return computerDAO.listAll();
    }
    /**.
     * list computer by page
     * @param start start index
     * @param offset page offset
     * @return instance of Page Object
     */
    public Page<Computer> listAllByPage(int start, int offset) {
        return computerDAO.listAllByPage(start, offset);
    }

    /**
     * return total number of element in computer table
     * @return number of elements in table
     */
    public Long count(){
        return computerDAO.count();
    }
}

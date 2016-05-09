package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.pagination.Page;
import com.excilys.cdb.validator.ComputerValidator;

public class ComputerServiceImpl implements ComputerService {
    private ComputerDAO computerDAO = null;
    private static ComputerServiceImpl computerService = null;

    /**.
     * ComputerService constructor
     */
    private ComputerServiceImpl() {
        computerDAO = ComputerDAO.getInstance();
    }

    /**.
     * return instance of ComputerService singleton
     * @return instance of ComputerService
     */
    public static synchronized ComputerServiceImpl getInstance() {
        if (computerService == null) {
            computerService = new ComputerServiceImpl();
        }
        return computerService;
    }

    @Override
    public Computer get(int id) {
        ComputerValidator.validateId(Integer.toString(id));
        return computerDAO.get(id);
    }

    @Override
    public Computer add(Computer comp) {
        ComputerValidator.validate(comp);
        return computerDAO.add(comp);
    }

    @Override
    public int delete(int id) {
        ComputerValidator.validateId(Integer.toString(id));
        return computerDAO.delete(id);
    }

    @Override
    public int update(int id, Computer c) {
        ComputerValidator.validateId(Integer.toString(id));
        ComputerValidator.validate(c);
        return computerDAO.update(id, c);
    }

    @Override
    public List<Computer> listAll() {
        return computerDAO.listAll();
    }

    @Override
    public Page<Computer> listAllByPage(int start, int offset) {
        return computerDAO.listAllByPage("", "id", start, offset);
    }

    @Override
    public Page<Computer> listByPage(String search, String order, int start, int offset) {
        return computerDAO.listAllByPage(search, order, start, offset);
    }

    @Override
    public Long count() {
        return computerDAO.count("");
    }

    @Override
    public Long count(String name) {
        return computerDAO.count(name);
    }
}

package com.excilys.cdb.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.pagination.Page;
import com.excilys.cdb.persistence.ConnectionFactory;
import com.excilys.cdb.persistence.ConnectionManager;
import com.excilys.cdb.validator.CompanyValidator;

public class CompanyServiceImpl implements CompanyService {
    private CompanyDAO companyDAO = null;
    private static CompanyServiceImpl companyService = null;
    ConnectionFactory connection = null;
    Connection con = null;
    static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    /**.
     * CompanyService constructor
     */
    private CompanyServiceImpl() {
        companyDAO = CompanyDAO.getInstance();
        connection = ConnectionFactory.getInstance();
    }

    /**.
     * return instance of singleton CompanyServce
     * @return CompanyService
     */
    public static synchronized CompanyServiceImpl getInstance() {
        if (companyService == null) {
            companyService = new CompanyServiceImpl();
        }
        return companyService;
    }

    @Override
    public Company get(int id) {
        CompanyValidator.validateId(Integer.toString(id));
        return companyDAO.get(id);
    }

    @Override
    public Company add(Company comp) {
        CompanyValidator.validate(comp);
        return companyDAO.add(comp);
    }

    @Override
    public int delete(int id) {
        CompanyValidator.validateId(Integer.toString(id));
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        connectionManager.init();

        int res = 0;
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        computerDAO.deleteAll(id);
        res = companyDAO.delete(id);

        try {
            connectionManager.commit();
        } catch (SQLException e) {
            connectionManager.rollback();
        } finally {
            connectionManager.close();
        }

        return res;
    }

    @Override
    public int update(int id, Company c) {
        CompanyValidator.validate(c);
        CompanyValidator.validateId(Integer.toString(id));
        return companyDAO.update(id, c);
    }

    @Override
    public List<Company> listAll() {
        return companyDAO.listAll();
    }

    @Override
    public Page<Company> listAllByPage(int start, int offset) {
        return companyDAO.listAllByPage("", "id", start, offset);
    }

    @Override
    public Page<Company> listByPage(String search, String order, int start, int offset) {
        return companyDAO.listAllByPage(search, order, start, offset);
    }

    @Override
    public Long count() {
        return companyDAO.count("");
    }

    @Override
    public Long count(String name) {
        return companyDAO.count(name);
    }
}

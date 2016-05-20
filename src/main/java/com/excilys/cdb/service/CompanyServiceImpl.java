package com.excilys.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.pagination.Page;
import com.excilys.cdb.validator.CompanyValidator;

@Service("companyService")
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    @Qualifier("companyDAO")
    private CompanyDAO companyDAO;
    @Autowired
    @Qualifier("computerDAO")
    private ComputerDAO computerDAO;

    static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

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
        int res = 0;
        computerDAO.deleteAll(id);
        res = companyDAO.delete(id);
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

package com.excilys.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.pagination.Page;
import com.excilys.cdb.validator.ComputerValidator;

@Service("computerService")
@Transactional
public class ComputerServiceImpl implements ComputerService {
    @Autowired
    @Qualifier("computerDAO")
    private ComputerDAO computerDAO;

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

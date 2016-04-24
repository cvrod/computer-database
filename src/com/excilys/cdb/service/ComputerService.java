package com.excilys.cdb.service;

import java.util.ArrayList;

import com.excilys.cdb.DAO.ComputerDAO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.pagination.Page;

public class ComputerService {
	private ComputerDAO computerDAO = null;
	private static ComputerService computerService = null;
	
	private ComputerService() {
		computerDAO = ComputerDAO.getInstance();
	}

	public static synchronized ComputerService getInstance() {
		if (computerService == null) {
			computerService = new ComputerService();
		}
		return computerService;
	}

	public Computer get(int id) {
		return computerDAO.get(id);
	}

	public Computer add(Computer comp) {
		return computerDAO.add(comp);
	}

	public int delete(int id) {
		return computerDAO.delete(id);
	}
	
	public int update(int id, Computer c) {
		return computerDAO.update(id, c);
	}
	
	public ArrayList<Computer> listAll() {
		return computerDAO.listAll();
	}
	
	public Page<Computer> listAllByPage(int start, int offset) {
		return computerDAO.listAllByPage(start, offset);
	}
	
}

package com.excilys.cdb.service;

import java.util.ArrayList;

import com.excilys.cdb.DAO.CompanyDAO;
import com.excilys.cdb.model.Company;

public class CompanyService {
	private CompanyDAO companyDAO = null;
	private static CompanyService companyService = null;
	
	private CompanyService() {
		companyDAO = CompanyDAO.getInstance();
	}

	public static synchronized CompanyService getInstance() {
		if (companyService == null) {
			companyService = new CompanyService();
		}
		return companyService;
	}

	public Company get(int id) {
		return companyDAO.get(id);
	}

	public Company add(Company comp) {
		return companyDAO.add(comp);
	}

	public int delete(int id) {
		return companyDAO.delete(id);
	}
	
	public int update(int id, Company c) {
		return companyDAO.update(id, c);
	}
	
	public ArrayList<Company> listAll() {
		return companyDAO.listAll();
	}
	
	public ArrayList<Company> listAllByPage(int start, int offset) {
		return companyDAO.listAllByPage(start, offset);
	}
	
}

package com.excilys.cdb.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.persistence.DBConnect;

/**
 * Main DAO class
 */
public abstract class GenericDAO<T> {
	protected DBConnect connection = null;
	CompanyMapper companyMapper = null;
	ComputerMapper computerMapper = null;
	public ResultSet setRes = null;
	StringBuffer res;
	final static Logger logger = LoggerFactory.getLogger(GenericDAO.class);
	
	public GenericDAO() {
		connection = DBConnect.getInstance();
	}
	
	public abstract ArrayList<T> listAll();
	
	public abstract ArrayList<T> listAllByPage(int start, int offset);
	
	public abstract int delete(int id);
	
	public abstract T add(T c);
	
	public abstract T get(int id);
	
	public abstract int update(int id, T c);
	
	
}

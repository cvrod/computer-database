package com.excilys.cdb.DAO;

import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CompanyDAO : handle company request
 * 
 * @see GenericDAO
 *
 */
public class CompanyDAO extends GenericDAO {
	
	final static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	public CompanyDAO() {
	}

	/**
	 * Getting a company under ResultSet form
	 * 
	 * @param id
	 *            Company id
	 * @return ResultSet containing Company information
	 */
	public ResultSet getCompanyById(int id) {
		logger.debug("getting a company by id");
		String req = "SELECT * FROM company WHERE id=" + id;
		connection.openConnection();
		setRes = connection.executeQuery(req);
		return setRes;
	}
}

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
	private static CompanyDAO _instance = null;

	public static CompanyDAO getInstance() {
		if (_instance == null) {
			_instance = new CompanyDAO();
		}
		return _instance;
	}
	
	private CompanyDAO() {
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

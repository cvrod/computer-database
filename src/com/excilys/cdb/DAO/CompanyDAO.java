package com.excilys.cdb.DAO;

import java.sql.ResultSet;

/**
 * CompanyDAO : handle company request
 * 
 * @see GenericDAO
 *
 */
public class CompanyDAO extends GenericDAO {

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
		String req = "SELECT * FROM company WHERE id=" + id;
		connection.openConnection();
		setRes = connection.executeQuery(req);
		return setRes;
	}
}

package com.excilys.cdb.DAO;

import java.sql.ResultSet;

/**
 * CompanyDAO : handle company request
 *
 */
public class CompanyDAO extends GenericDAO {

	public CompanyDAO() {
	}

	public ResultSet getCompanyById(int id) {
		String req = "SELECT * FROM company WHERE id=" + id;
		connection.openConnection();
		setRes = connection.executeQuery(req);
		return setRes;
	}
}

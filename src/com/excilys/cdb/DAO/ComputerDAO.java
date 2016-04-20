package com.excilys.cdb.DAO;

import java.sql.ResultSet;

/**
 * Computer DAO, handle computer request
 * 
 * @see GenericDAO
 */
public class ComputerDAO extends GenericDAO {

	CompanyDAO companyDAO = null;

	public ComputerDAO() {
		companyDAO = new CompanyDAO();
	}

	/**
	 * Remove a computer from base
	 * 
	 * @param id
	 *            computer id to delete
	 * @return int : number of row affected (0 or 1)
	 */
	public int deleteComputer(int id) {
		String req = "DELETE FROM computer WHERE id = " + id + ";";
		int res = -1;
		connection.openConnection();
		res = connection.executeUpdate(req);
		connection.closeConnection();
		return res;
	}

	/**
	 * Getting computer detail from base
	 * 
	 * @param id
	 *            computer id to display
	 * @return ResultSet who contain computer detail
	 */
	public ResultSet getComputerDetail(int id) {
		String req = "SELECT * FROM computer WHERE id = " + id + ";";
		connection.openConnection();
		setRes = connection.executeQuery(req);
		return setRes;
	}
}

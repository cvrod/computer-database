package com.excilys.cdb.DAO;

import java.sql.ResultSet;

import com.excilys.cdb.model.Computer;

/**
 * Computer DAO, handle computer request
 * 
 * @see GenericDAO
 */
public class ComputerDAO extends GenericDAO {

	CompanyDAO companyDAO = null;
	public static final String NULL_TIMESTAMP = "0000-00-00";

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
	 * Adding Computer to database
	 * 
	 * @param c
	 *            computer to add
	 * @return 0 if Insert fail, 1 if everything is fine
	 */
	public int addingComputer(Computer c) {
		String introduced;
		String discontinued;
		Long companyId;

		if (c.getIntroduced() == null) {
			introduced = NULL_TIMESTAMP;
		} else {
			introduced = c.getIntroduced().toString();
		}
		if (c.getDiscontinued() == null) {
			discontinued = NULL_TIMESTAMP;
		} else {
			discontinued = c.getDiscontinued().toString();
		}
		if (c.getCompany() == null) {
			companyId = null;
		} else {
			companyId = c.getCompany().getId();
		}

		String req = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES ('" + c.getName() + "', '"
				+ introduced + "', '" + discontinued + "', " + companyId + ")";
		System.out.println(req);
		connection.openConnection();
		int res = connection.executeUpdate(req);
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

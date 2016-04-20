package com.excilys.cdb.DAO;

import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Computer;

/**
 * Computer DAO, handle computer request
 * 
 * @see GenericDAO
 */
public class ComputerDAO extends GenericDAO {

	CompanyDAO companyDAO = null;
	public static final String NULL_TIMESTAMP = "0000-00-00";
	final static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

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
		logger.debug("delete Computer");
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
		logger.debug("adding Computer");
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
		logger.debug("getting computer detail");
		String req = "SELECT * FROM computer WHERE id = " + id + ";";
		connection.openConnection();
		setRes = connection.executeQuery(req);
		return setRes;
	}
	/**
	 * update a computer detail in base
	 * @param id id of the computer to update
	 * @param tmpComputer Computer object containing fresh infos
	 * @return 0 if Update fail, 1 if success
	 */
	public int updateComputer(int id, Computer tmpComputer) {
		// TODO Auto-generated method stub
		return 0;
	}
}

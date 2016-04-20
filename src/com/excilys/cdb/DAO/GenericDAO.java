package com.excilys.cdb.DAO;

import java.sql.ResultSet;

import com.excilys.cdb.exception.UnknowTypeException;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.persistence.DBConnect;

/**
 * Main DAO class
 */
public abstract class GenericDAO {
	protected DBConnect connection = null;
	public static final String COMPUTER_TABLE = "computer";
	public static final String COMPANY_TABLE = "company";
	CompanyMapper companyMapper = null;
	public ResultSet setRes = null;
	StringBuffer res;

	public GenericDAO() {
		connection = DBConnect.getInstance();
	}

	/**
	 * Ask database for all Computer or Campany
	 * 
	 * @param type
	 *            GenericDAO.COMPUTER_TABLE or GenericDAO.COMPANY_TABLE
	 * @return ResultSet containing all computer or company
	 * @throws UnknowTypeException
	 */
	public ResultSet listAll(String type) throws UnknowTypeException {
		if (type.equals(COMPUTER_TABLE) || type.equals(COMPANY_TABLE)) {

			String req = "SELECT * FROM " + type + ";";
			connection.openConnection();
			setRes = connection.executeQuery(req);
			return setRes;
		} else {
			throw new UnknowTypeException();
		}
	}
}

package com.excilys.cdb.DAO;

import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	final static Logger logger = LoggerFactory.getLogger(GenericDAO.class);
	
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
		logger.info("listAll " + type);
		if (type.equals(COMPUTER_TABLE) || type.equals(COMPANY_TABLE)) {

			String req = "SELECT * FROM " + type + ";";
			connection.openConnection();
			setRes = connection.executeQuery(req);
			return setRes;
		} else {
			throw new UnknowTypeException();
		}
	}

	public ResultSet listAllByPage(String type, int start, int offset) throws UnknowTypeException {
		logger.info("listAllByPage : start :" + start + " offset : " + offset);
		if (type.equals(COMPUTER_TABLE) || type.equals(COMPANY_TABLE)) {

			String req = "SELECT * FROM " + type + " LIMIT "+start+", "+offset+";";
			connection.openConnection();
			setRes = connection.executeQuery(req);
			return setRes;
		} else {
			throw new UnknowTypeException();
		}
	}
}

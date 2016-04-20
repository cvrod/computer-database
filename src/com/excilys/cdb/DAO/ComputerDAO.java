package com.excilys.cdb.DAO;

import java.sql.ResultSet;


/**
 * Computer DAO, handle computer request
 */
public class ComputerDAO extends GenericDAO {

	CompanyDAO companyDAO = null;

	public ComputerDAO() {
		companyDAO = new CompanyDAO();
	}
	
	public int deleteComputer(int id){
		String req = "DELETE FROM computer WHERE id = " + id + ";";	
		int res = -1;
		connection.openConnection();
		res = connection.executeUpdate(req);
		connection.closeConnection();
		return res;
	}

	public ResultSet getComputerDetail(int id) {
		String req = "SELECT * FROM computer WHERE id = " + id + ";";
		connection.openConnection();
		setRes = connection.executeQuery(req);
		return setRes;
	}
}

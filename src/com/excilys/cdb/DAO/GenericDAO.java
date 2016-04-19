package com.excilys.cdb.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.exception.UnknowTypeException;
import com.excilys.cdb.persistence.DBConnect;

public abstract class GenericDAO {
	protected DBConnect connection = null;
	public static final String COMPUTER_TABLE = "computer";
	public static final String COMPANY_TABLE = "company";
	public ResultSet setRes = null;
	StringBuffer res;

	public GenericDAO() {
		connection = DBConnect.getInstance();
	}

	public StringBuffer listAll(String type) throws UnknowTypeException {
		if (type.equals(COMPUTER_TABLE) || type.equals(COMPANY_TABLE)) {

			String req = "SELECT * FROM " + type + ";";

			connection.openConnection();
			setRes = connection.executeQuery(req);

			res = new StringBuffer();

			try {
				while (setRes.next()) {
					res.append("ID : ");
					res.append(setRes.getInt("id"));
					res.append(", name : ");
					res.append(setRes.getString("name"));
					res.append("\n");
				}
				connection.closeConnection();
				return res;
			} catch (SQLException e) {
				System.out.println("Cannot find name in table " + type);
			}
		} else {
			throw new UnknowTypeException();
		}
		return null;
	}
}

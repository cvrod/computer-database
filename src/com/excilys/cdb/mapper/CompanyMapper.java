package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DBConnect;

/**
 * Company mapper, who convert a given ResultSet to a List of Company
 * 
 * @see Mapper
 */
public class CompanyMapper implements Mapper<Company> {
	public static final String ID = "id";
	public static final String NAME = "name";
	public DBConnect connection = null;

	static CompanyMapper _instance = null;

	public static CompanyMapper getInstance() {
		if (_instance == null) {
			_instance = new CompanyMapper();
		}
		return _instance;
	}

	private CompanyMapper() {
		connection = DBConnect.getInstance();
	}

	/**
	 * Getting an ArrayList of Company from a ResultSet
	 * 
	 * @param setRes
	 *            resultSet to convert
	 * @return ArrayList<Company> containing all companies informations
	 */
	@Override
	public ArrayList<Company> map(ResultSet setRes) {
		ArrayList<Company> res = new ArrayList<>();
		try {
			while (setRes.next()) {
				Company tmp = new Company(setRes.getLong(ID), setRes.getString(NAME));
				res.add(tmp);
			}
		} catch (SQLException e) {
			System.out.println("Campany table error !");
		}
		connection.closeConnection();
		return res;
	}
}

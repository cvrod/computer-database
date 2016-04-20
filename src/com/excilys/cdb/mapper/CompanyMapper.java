package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DBConnect;

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
	
	private CompanyMapper(){
		connection = DBConnect.getInstance();
	}
	
	@Override
	public ArrayList<Company> map(ResultSet setRes) {
		ArrayList<Company> res = new ArrayList<>();
		try {
			while (setRes.next()) {
				Company tmp = new Company(setRes.getInt(ID), setRes.getString(NAME));
				res.add(tmp);
			}
		} catch (SQLException e) {
			System.out.println("Campany table error !");
		}
		connection.closeConnection();
		return res;
	}
}

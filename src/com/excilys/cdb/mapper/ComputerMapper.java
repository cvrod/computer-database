package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.cdb.DAO.CompanyDAO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DBConnect;

public class ComputerMapper implements Mapper<Computer> {
	public static final String ID = "id";
	public static final String NAME = "name";
	public CompanyMapper companyMapper = null;
	public CompanyDAO companyDAO = null;
	public DBConnect connection = null;
	
	static ComputerMapper _instance = null;
	
	public static ComputerMapper getInstance() {
		if (_instance == null) {
			_instance = new ComputerMapper();
		}
		return _instance;
	}
	
	private ComputerMapper(){
		companyMapper = CompanyMapper.getInstance();
		companyDAO = new CompanyDAO();
		connection = DBConnect.getInstance();
	}
	
	@Override
	public ArrayList<Computer> map(ResultSet setRes) {
		ArrayList<Computer> res = new ArrayList<>();
		ArrayList<Company> companyList = new ArrayList<>();
		Company c = null;
		int id;
		String name;
		String introduced;
		String discontinued;
		int companyId;
		try {
			while (setRes.next()) {
				id = setRes.getInt("id");
				name = setRes.getString("name");
				introduced = setRes.getString("introduced");
				discontinued = setRes.getString("discontinued");
				companyId = setRes.getInt("company_id");
				if(companyId != 0){
					ResultSet resCompany = companyDAO.getCompanyById(companyId);
					companyList = companyMapper.map(resCompany);
					c = companyList.get(0);
				}
				Computer tmp = new Computer(id, name, introduced, discontinued, c);
				res.add(tmp);
			}
		} catch (SQLException e) {
			System.out.println("Campany table error !");
		}
		connection.closeConnection();
		return res;
	}
}

package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.DAO.CompanyDAO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DBConnect;

/**
 * Computer mapper, who convert a given ResultSet to a List of Computer
 * @see Mapper
 */
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
		companyDAO = CompanyDAO.getInstance();
		connection = DBConnect.getInstance();
	}
	/**
	 * Getting an ArrayList of Computer from a ResultSet
	 * 
	 * @param setRes
	 *            resultSet to convert
	 * @return ArrayList<Company> containing all computers informations
	 */
	@Override
	public List<Computer> map(ResultSet setRes) {
		ArrayList<Computer> res = new ArrayList<>();
		Company c = null;
		Long id;
		String name;
		String introduced;
		String discontinued;
		int companyId;
		try {
			while (setRes.next()) {
				id = setRes.getLong("id");
				name = setRes.getString("name");
				introduced = setRes.getString("introduced");
				discontinued = setRes.getString("discontinued");
				companyId = setRes.getInt("company_id");
				if(companyId != 0){
					c = companyDAO.get(companyId);
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

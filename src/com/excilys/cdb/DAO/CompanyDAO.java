package com.excilys.cdb.DAO;

import java.sql.SQLException;

import com.excilys.cdb.persistence.DBConnect;

public class CompanyDAO extends GenericDAO{

	public CompanyDAO(){
		connection = DBConnect.getInstance();
	}
	
	public String find(int id){
		String req = "SELECT name FROM company WHERE id=" + id;
		
		connection.openConnection();
		setRes = connection.executeQuery(req);
		
		String resStr = null;
		
		try {
			while (setRes.next()) {
				resStr = setRes.getString("name");
			}
			connection.closeConnection();
			return resStr;
		} catch (SQLException e) {
			System.out.println("Cannot find name in table company");
		}
		return resStr;
	}
}

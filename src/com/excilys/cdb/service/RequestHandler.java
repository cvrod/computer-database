package com.excilys.cdb.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.exception.UnknowTypeException;
import com.excilys.cdb.persistence.DAOConnect;

public class RequestHandler {
	protected DAOConnect connection = null;
	public static final String COMPUTER_TABLE = "computer";
	public static final String COMPANY_TABLE = "company";
	public ResultSet setRes = null;
	StringBuffer res;

	public RequestHandler() {
		connection = DAOConnect.getInstance();
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
					res.append(" ,name : ");
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

	public StringBuffer getComputerDetail(int id) {
		System.out.println("id : " + id);

		String req = "SELECT * FROM computer WHERE id = " + id + ";";
		connection.openConnection();
		setRes = connection.executeQuery(req);
		
		res = new StringBuffer();
		boolean isEmpty = true;
		
		try {
			while (setRes.next()) {
				isEmpty = false;
				res.append("ID : ");
				res.append(setRes.getInt("id"));
				res.append(", name : ");
				res.append(setRes.getString("name"));
				String tmp = setRes.getString("introduced");
				if(!(tmp == null)){
					res.append(", introduced : ");
					res.append(tmp);
				}
				tmp = setRes.getString("discontinued");
				if(!(tmp == null)){
					res.append(", discontinued : ");
					res.append(tmp);
				}
				int tmpId = setRes.getInt("company_id");
				if(tmpId != 0){
					res.append(", company : ");
					res.append(getCompanyNameFromId(tmpId));
				}
			}
			connection.closeConnection();
			if(isEmpty){
				res.append("Computer ");
				res.append(id);
				res.append(" doesn't exist !");
			}
		} catch (SQLException e) {

		}
		return res;
	}
	
	public String getCompanyNameFromId(int id){
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

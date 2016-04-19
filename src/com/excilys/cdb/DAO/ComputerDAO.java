package com.excilys.cdb.DAO;

import java.sql.SQLException;

import com.excilys.cdb.persistence.DBConnect;

/**
 * Computer DAO, handle computer request
 */
public class ComputerDAO extends GenericDAO {

	CompanyDAO companyDAO = null;

	public ComputerDAO() {
		connection = DBConnect.getInstance();
		companyDAO = new CompanyDAO();
	}
	
	public StringBuffer deleteComputer(int id){
		String req = "DELETE FROM computer WHERE id = " + id + ";";
		StringBuffer str = new StringBuffer();
		
		int res = -1;
		connection.openConnection();
		res = connection.executeUpdate(req);
		connection.closeConnection();
		if(res == 1){
			return str.append("Delete Success !");
		}
		else if(res == 0){
			str.append(id);
			str.append(" doesn't match any computer");
			return str;
		}
		else{
			return str.append("Unexpected result...");
		}
	}

	public StringBuffer getComputerDetail(int id) {

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
				if (!(tmp == null)) {
					res.append(", introduced : ");
					res.append(tmp.split(" ")[0]);
				}
				tmp = setRes.getString("discontinued");
				if (!(tmp == null)) {
					res.append(", discontinued : ");
					res.append(tmp.split(" ")[0]);
				}
				int tmpId = setRes.getInt("company_id");
				if (tmpId != 0) {
					res.append(", company : ");
					res.append(companyDAO.getCompanyNameById(tmpId));
				}
			}
			connection.closeConnection();
			if (isEmpty) {
				res.append("Computer ");
				res.append(id);
				res.append(" doesn't exist !");
			}
		} catch (SQLException e) {

		}
		return res;
	}
}

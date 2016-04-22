package com.excilys.cdb.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;

/**
 * Computer DAO, handle computer request
 * 
 * @see GenericDAO
 */
public class ComputerDAO extends GenericDAO {

	CompanyDAO companyDAO = null;
	public static final String INSERT_REQUEST = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
	public static final String DETAIL_REQUEST = "SELECT * FROM computer WHERE id=?";
	public static final String DELETE_REQUEST = "DELETE FROM computer WHERE id=?";
	public static final String UPDATE_REQUEST = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
	final static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	Connection con = null;

	private static ComputerDAO _instance = null;

	public static ComputerDAO getInstance() {
		if (_instance == null) {
			_instance = new ComputerDAO();
		}
		return _instance;
	}

	private ComputerDAO() {
		companyDAO = CompanyDAO.getInstance();
	}

	/**
	 * Remove a computer from base
	 * 
	 * @param id
	 *            computer id to delete
	 * @return int : number of row affected (0 or 1)
	 */
	public int deleteComputer(int id) {
		logger.debug("delete Computer");
		int res = -1;

		PreparedStatement stmt = null;
		connection.openConnection();
		con = connection.getConnection();

		try {
			stmt = con.prepareStatement(DELETE_REQUEST);
			stmt.setInt(1, id);
			res = stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				stmt.close();
				connection.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	/**
	 * Adding Computer to database
	 * 
	 * @param c
	 *            computer to add
	 * @return 0 if Insert fail, 1 if everything is fine
	 */
	public int addingComputer(Computer c) {
		logger.debug("adding Computer");

		PreparedStatement stmt = null;
		connection.openConnection();
		con = connection.getConnection();

		try {
			stmt = con.prepareStatement(INSERT_REQUEST);
			stmt.setString(1, c.getName());
			if (c.getIntroduced() == null) {
				stmt.setNull(2, java.sql.Types.TIMESTAMP);
			} else {
				stmt.setDate(2, Date.valueOf(c.getIntroduced()));
			}
			if (c.getDiscontinued() == null) {
				stmt.setNull(3, java.sql.Types.TIMESTAMP);
			} else {
				stmt.setDate(3, Date.valueOf(c.getDiscontinued()));
			}
			if (c.getCompany() == null) {
				stmt.setNull(4, java.sql.Types.BIGINT);
			} else {
				stmt.setLong(4, c.getCompany().getId());
			}
			int res = stmt.executeUpdate();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				connection.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * Getting computer detail from base
	 * 
	 * @param id
	 *            computer id to display
	 * @return Computer 
	 */
	public Computer getComputerDetail(int id) {
		logger.debug("getting computer detail");

		connection.openConnection();
		con = connection.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Computer> computerList = null;

		try {
			stmt = con.prepareStatement(DETAIL_REQUEST);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			computerMapper = ComputerMapper.getInstance();
			computerList = (ArrayList<Computer>) computerMapper.map(rs);
			if (computerList.size() >= 1) {
				logger.info("Computer Found : " + id);
				return computerList.get(0);
			} else {
				logger.warn("Couldn't find Computer : " + id);
				return null;
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				rs.close();
				stmt.close();
				connection.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * update a computer detail in base
	 * 
	 * @param id
	 *            id of the computer to update
	 * @param tmpComputer
	 *            Computer object containing fresh infos
	 * @return 0 if Update fail, 1 if success
	 */
	public int updateComputer(int id, Computer c) {
		logger.debug("update Computer");

		PreparedStatement stmt = null;
		connection.openConnection();
		con = connection.getConnection();

		try {
			stmt = con.prepareStatement(UPDATE_REQUEST);
			stmt.setString(1, c.getName());
			if (c.getIntroduced() == null) {
				stmt.setNull(2, java.sql.Types.TIMESTAMP);
			} else {
				stmt.setDate(2, Date.valueOf(c.getIntroduced()));
			}
			if (c.getDiscontinued() == null) {
				stmt.setNull(3, java.sql.Types.TIMESTAMP);
			} else {
				stmt.setDate(3, Date.valueOf(c.getDiscontinued()));
			}
			if (c.getCompany() == null) {
				stmt.setNull(4, java.sql.Types.BIGINT);
			} else {
				stmt.setLong(4, c.getCompany().getId());
			}
			stmt.setInt(5, id);
			int res = stmt.executeUpdate();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				connection.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}

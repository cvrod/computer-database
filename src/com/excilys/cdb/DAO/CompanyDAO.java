package com.excilys.cdb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.mysql.jdbc.Statement;

/**
 * CompanyDAO : handle company request
 * 
 * @see GenericDAO
 *
 */
public class CompanyDAO extends GenericDAO<Company> {

	final static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	private static CompanyDAO _instance = null;
	public static final String DETAIL_REQUEST = "SELECT * FROM company WHERE id=?";
	public static final String LISTALL_REQUEST = "SELECT * FROM company;";
	public static final String LISTPAGE_REQUEST = "SELECT * FROM company LIMIT ?,?";
	public static final String DELETE_REQUEST = "DELETE FROM company WHERE id=?";
	public static final String INSERT_REQUEST = "INSERT INTO company (name) VALUES(?)";
	public static final String UPDATE_REQUEST = "UPDATE company SET name=? WHERE id=?";
	Connection con = null;

	public static synchronized CompanyDAO getInstance() {
		if (_instance == null) {
			_instance = new CompanyDAO();
		}
		return _instance;
	}

	private CompanyDAO() {
	}

	/**
	 * Getting a company under ResultSet form
	 * 
	 * @param id
	 *            Company id
	 * @return ResultSet containing Company information
	 */
	@Override
	public Company get(int id) {
		logger.debug("getting a company by id");
		connection.openConnection();
		ResultSet rs = null;
		ArrayList<Company> companyList = null;

		try (Connection con = connection.getConnection();
				PreparedStatement stmt = con.prepareStatement(DETAIL_REQUEST)) {
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			companyMapper = CompanyMapper.getInstance();
			companyList = (ArrayList<Company>) companyMapper.map(rs);
			if (companyList.size() >= 1) {
				logger.info("Computer Found : " + id);
				return companyList.get(0);
			} else {
				logger.warn("Couldn't find Computer : " + id);
				return null;
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Ask database for all Company
	 * 
	 * @return ArrayList<Company> all computer list
	 */
	@Override
	public ArrayList<Company> listAll() {
		logger.debug("List all company");

		connection.openConnection();
		ResultSet rs = null;

		try (Connection con = connection.getConnection();
				PreparedStatement stmt = con.prepareStatement(LISTALL_REQUEST)) {
			rs = stmt.executeQuery();
			companyMapper = CompanyMapper.getInstance();
			return (ArrayList<Company>) companyMapper.map(rs);

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Ask database for Company by page
	 * 
	 * @return ArrayList<Company> company list
	 */
	@Override
	public ArrayList<Company> listAllByPage(int start, int offset) {
		logger.debug("List company by Page");

		connection.openConnection();
		ResultSet rs = null;

		try (Connection con = connection.getConnection();
				PreparedStatement stmt = con.prepareStatement(LISTPAGE_REQUEST)) {
			stmt.setInt(1, start);
			stmt.setInt(2, offset);
			rs = stmt.executeQuery();
			companyMapper = CompanyMapper.getInstance();
			return (ArrayList<Company>) companyMapper.map(rs);

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Remove a company from base
	 * 
	 * @param id
	 *            company id to delete
	 * @return int : number of row affected (0 or 1)
	 */
	@Override
	public int delete(int id) {
		logger.debug("delete Company");
		int res = -1;

		connection.openConnection();

		try (Connection con = connection.getConnection();
				PreparedStatement stmt = con.prepareStatement(DELETE_REQUEST)) {
			stmt.setInt(1, id);
			res = stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return res;
	}

	/**
	 * Adding Company to database
	 * 
	 * @param c
	 *            company to add
	 * @return 0 if Insert fail, 1 if everything is fine
	 */
	@Override
	public Company add(Company c) {
		logger.debug("adding Company");

		connection.openConnection();

		try (Connection con = connection.getConnection();
				PreparedStatement stmt = con.prepareStatement(INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, c.getName());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			c.setId(rs.getLong(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * update a company detail in base
	 * 
	 * @param id
	 *            id of the company to update
	 * @param c
	 *            Company object containing fresh infos
	 * @return 0 if Update fail, 1 if success
	 */
	@Override
	public int update(int id, Company c) {
		logger.debug("update Computer");

		connection.openConnection();

		try (Connection con = connection.getConnection();
				PreparedStatement stmt = con.prepareStatement(UPDATE_REQUEST)) {
			stmt.setString(1, c.getName());
			stmt.setInt(2, id);
			int res = stmt.executeUpdate();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}

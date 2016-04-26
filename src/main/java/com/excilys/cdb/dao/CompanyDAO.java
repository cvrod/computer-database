package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.pagination.Page;
import com.mysql.jdbc.Statement;

/**.
 * CompanyDAO : handle company request
 *
 * @see GenericDAO
 *
 */
public class CompanyDAO extends GenericDAO<Company> {

    static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);
    private static CompanyDAO instance = null;
    public static final String DETAIL_REQUEST = "SELECT * FROM company WHERE id=?";
    public static final String LISTALL_REQUEST = "SELECT * FROM company;";
    public static final String LISTPAGE_REQUEST = "SELECT * FROM company LIMIT ?,?";
    public static final String DELETE_REQUEST = "DELETE FROM company WHERE id=?";
    public static final String INSERT_REQUEST = "INSERT INTO company (name) VALUES(?)";
    public static final String UPDATE_REQUEST = "UPDATE company SET name=? WHERE id=?";
    public static final String COUNT_REQUEST = "SELECT COUNT(*) FROM company";
    Connection con = null;

    /**.
     * Getting instance of CompanyDAO Singleton
     * @return CompanyDAO Object
     */
    public static synchronized CompanyDAO getInstance() {
        if (instance == null) {
            instance = new CompanyDAO();
        }
        return instance;
    }

    /**.
     * Default CompanyDAO Constructor
     */
    private CompanyDAO() {
    }

    /**.
     * Getting a company under ResultSet form
     *
     * @param id
     *            Company id
     * @return ResultSet containing Company information
     */
    @Override
    public Company get(int id) {
        LOGGER.debug("getting a company by id");
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
                LOGGER.info("Found company of id : " + id);
                return companyList.get(0);
            } else {
                LOGGER.warn("Couldn't find Company of id : " + id);
                return null;
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    /**.
     * Ask database for all Company
     *
     * @return ArrayList<Company> all computer list
     */
    @Override
    public ArrayList<Company> listAll() {
        LOGGER.debug("List all company");

        connection.openConnection();
        ResultSet rs = null;

        try (Connection con = connection.getConnection();
                PreparedStatement stmt = con
                        .prepareStatement(LISTALL_REQUEST)) {
            rs = stmt.executeQuery();
            companyMapper = CompanyMapper.getInstance();
            return (ArrayList<Company>) companyMapper.map(rs);

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    /**.
     * Ask database for Company by page
     *
     * @return ArrayList<Company> company list
     */
    @Override
    public Page<Company> listAllByPage(int start, int offset) {
        if (start < 0 || offset < 0) {
            return null;
        }
        LOGGER.debug("List company by Page");
        connection.openConnection();

        ResultSet rs = null;
        ArrayList<Company> elementList = null;

        try (Connection con = connection.getConnection();
                PreparedStatement stmt = con
                        .prepareStatement(LISTPAGE_REQUEST)) {
            stmt.setInt(1, start);
            stmt.setInt(2, offset);
            rs = stmt.executeQuery();
            companyMapper = CompanyMapper.getInstance();
            elementList = (ArrayList<Company>) companyMapper.map(rs);
            Page<Company> page = new Page<>(elementList, start, offset);

            return page;

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    /**.
     * Remove a company from base
     *
     * @param id
     *            company id to delete
     * @return int : number of row affected (0 or 1)
     */
    @Override
    public int delete(int id) {
        LOGGER.debug("delete Company");
        int res = -1;

        connection.openConnection();

        try (Connection con = connection.getConnection();
                PreparedStatement stmt = con.prepareStatement(DELETE_REQUEST)) {
            stmt.setInt(1, id);
            res = stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
        return res;
    }

    /**.
     * Adding Company to database
     *
     * @param c
     *            company to add
     * @return 0 if Insert fail, 1 if everything is fine
     */
    @Override
    public Company add(Company c) {
        LOGGER.debug("adding Company");

        connection.openConnection();

        try (Connection con = connection.getConnection();
                PreparedStatement stmt = con.prepareStatement(INSERT_REQUEST,
                        Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, c.getName());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            c.setId(rs.getLong(1));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
        return c;
    }

    /**.
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
        LOGGER.debug("update Computer");
        int res = 0;
        connection.openConnection();

        try (Connection con = connection.getConnection();
                PreparedStatement stmt = con.prepareStatement(UPDATE_REQUEST)) {
            stmt.setString(1, c.getName());
            stmt.setInt(2, id);
            res = stmt.executeUpdate();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }
    
    @Override
    public Long count() {
        LOGGER.debug("count company");
        ResultSet rs = null;
        connection.openConnection();
        try (Connection con = connection.getConnection();
                PreparedStatement stmt = con.prepareStatement(COUNT_REQUEST)) {
            rs = stmt.executeQuery();
            rs.next();
            return rs.getLong(1);
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }
}

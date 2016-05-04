package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public static final String LISTPAGE_REQUEST = "SELECT * FROM company WHERE company.name LIKE ? ORDER BY %s LIMIT ?,?";
    public static final String DELETE_REQUEST = "DELETE FROM company WHERE id=?";
    public static final String INSERT_REQUEST = "INSERT INTO company (name) VALUES(?)";
    public static final String UPDATE_REQUEST = "UPDATE company SET name=? WHERE id=?";
    public static final String COUNT_REQUEST = "SELECT COUNT(*) FROM company WHERE name like ?";
    public static final String DELETE_COMPUTER = "DELETE FROM computer WHERE company_id=?";
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
        ResultSet rs = null;
        ArrayList<Company> companyList = null;

        try (Connection con = connection.openConnection();
                PreparedStatement stmt = con.prepareStatement(DETAIL_REQUEST)) {
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            companyMapper = CompanyMapper.getInstance();
            companyList = (ArrayList<Company>) companyMapper.map(rs);
            if (companyList.size() >= 1) {
                LOGGER.debug("getting company of id : " + id);
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
    public List<Company> listAll() {
        LOGGER.debug("List all company");

        ResultSet rs = null;

        try (Connection con = connection.openConnection();
                PreparedStatement stmt = con
                        .prepareStatement(LISTALL_REQUEST)) {
            rs = stmt.executeQuery();
            companyMapper = CompanyMapper.getInstance();
            return companyMapper.map(rs);

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
    public Page<Company> listAllByPage(String name, String order, int start, int offset) {
        if (start < 0 || offset < 0) {
            return null;
        }
        LOGGER.debug("List company by Page");

        ResultSet rs = null;
        ArrayList<Company> elementList = null;
        String request = null;

        if (order.equals("id") || order.equals("name")
                || order.equals("introduced") || order.equals("discontinued")
                || order.equals("company_id")) {
            request = String.format(LISTPAGE_REQUEST, order);
        } else {
            request = String.format(LISTPAGE_REQUEST, "id");
        }

        try (Connection con = connection.openConnection();
                PreparedStatement stmt = con
                        .prepareStatement(request)) {
            stmt.setString(1, "%" + name + "%");
            stmt.setInt(2, start);
            stmt.setInt(3, offset);
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
        LOGGER.debug("delete Company and Computer");
        int res = -1;

        try (Connection con = connection.openConnection();
                PreparedStatement stmtComputer = con.prepareStatement(DELETE_COMPUTER)) {
            con.setAutoCommit(false);
            stmtComputer.setInt(1, id);
            stmtComputer.executeUpdate();

            PreparedStatement stmt = con.prepareStatement(DELETE_REQUEST);
            stmt.setInt(1, id);
            res = stmt.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
            } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException e1) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e1);
            }
            throw new DAOException(e);
        }
        return res;
    }

    /**.
     * Remove a company from base (using transaction)
     *
     * @param id
     *            company id to delete
     * @return int : number of row affected (0 or 1)
     */
    public int delete2(Connection con, int companyId) {
        LOGGER.debug("delete a Company");
        int res = 0;
        try (PreparedStatement stmt = con.prepareStatement(DELETE_REQUEST)) {
            stmt.setInt(1, companyId);
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

        try (Connection con = connection.openConnection();
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

        try (Connection con = connection.openConnection();
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
    public Long count(String name) {
        LOGGER.debug("count company");
        ResultSet rs = null;

        try (Connection con = connection.openConnection();
                PreparedStatement stmt = con.prepareStatement(COUNT_REQUEST)) {
            stmt.setString(1, "%" + name + "%");
            rs = stmt.executeQuery();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }
}

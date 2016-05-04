package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.pagination.Page;
import com.mysql.jdbc.Statement;

/**
 * . Computer DAO, handle computer request
 *
 * @see GenericDAO
 */
public class ComputerDAO extends GenericDAO<Computer> {

    CompanyDAO companyDAO = null;
    public static final String INSERT_REQUEST = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
    public static final String DETAIL_REQUEST = "SELECT * FROM computer WHERE id=?";
    public static final String DELETE_REQUEST = "DELETE FROM computer WHERE id=?";
    public static final String UPDATE_REQUEST = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
    public static final String LISTALL_REQUEST = "SELECT * FROM computer;";
    public static final String LISTPAGE_REQUEST = "SELECT * FROM computer WHERE computer.name LIKE ? ORDER BY %s LIMIT ?,?";
    public static final String COUNT_REQUEST = "SELECT COUNT(*) FROM computer WHERE name LIKE ?";
    public static final String DELETE_ALL_REQUEST = "DELETE FROM computer WHERE company_id=?";
    static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);
    Connection con = null;

    private static ComputerDAO instance = null;

    /**
     * . getting instance of ComputerDAO Singleton
     *
     * @return ComputerDAO Instance
     */
    public static synchronized ComputerDAO getInstance() {
        if (instance == null) {
            instance = new ComputerDAO();
        }
        return instance;
    }

    /**
     * . default constructor
     */
    private ComputerDAO() {
        companyDAO = CompanyDAO.getInstance();
    }

    /**
     * . Remove a computer from base
     *
     * @param id
     *            computer id to delete
     * @return int : number of row affected (0 or 1)
     */
    public int delete(int id) {
        LOGGER.debug("delete Computer");
        int res = -1;

        try (Connection con = connection.openConnection();
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
     * Delete all computer from a given companyID
     * @param con connection to use
     * @param companyId if of company
     */
    public void deleteAll(Connection con, int companyId) {
        LOGGER.debug("delete All Computer from a company ID");

        try (PreparedStatement stmt = con.prepareStatement(DELETE_ALL_REQUEST)) {
            stmt.setInt(1, companyId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * . Ask database for all Computer
     *
     * @return ArrayList<Computer> all computer list
     */
    @Override
    public List<Computer> listAll() {
        LOGGER.debug("List all computer");

        ResultSet rs = null;

        try (Connection con = connection.openConnection();
                PreparedStatement stmt = con
                        .prepareStatement(LISTALL_REQUEST)) {
            rs = stmt.executeQuery();
            computerMapper = ComputerMapper.getInstance();
            return computerMapper.map(rs);

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * . Ask database for Computer by page
     *
     * @return ArrayList<Computer> computer list
     */
    @Override
    public Page<Computer> listAllByPage(String name, String order, int start,
            int offset) {
        if (start < 0 || offset < 0) {
            return null;
        }
        LOGGER.debug("List computer by Page");
        ArrayList<Computer> elementList = null;
        ResultSet rs = null;
        String request = null;

        if (!order.equals("")) {
            request = String.format(LISTPAGE_REQUEST, order);
        } else {
            request = String.format(LISTPAGE_REQUEST, "id");
        }

        try (Connection con = connection.openConnection();
                PreparedStatement stmt = con.prepareStatement(request)) {
            stmt.setString(1, "%" + name + "%");
            stmt.setInt(2, start);
            stmt.setInt(3, offset);
            rs = stmt.executeQuery();
            computerMapper = ComputerMapper.getInstance();

            elementList = (ArrayList<Computer>) computerMapper.map(rs);
            Page<Computer> page = new Page<>(elementList, start, offset);

            return page;

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * . Adding Computer to database
     *
     * @param c
     *            computer to add
     * @return 0 if Insert fail, 1 if everything is fine
     */
    public Computer add(Computer c) {
        LOGGER.debug("adding Computer");

        try (Connection con = connection.openConnection();
                PreparedStatement stmt = con.prepareStatement(INSERT_REQUEST,
                        Statement.RETURN_GENERATED_KEYS)) {
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

    /**
     * . Getting computer detail from base
     *
     * @param id
     *            computer id to display
     * @return Computer
     */
    public Computer get(int id) {
        LOGGER.debug("getting computer detail");

        ResultSet rs = null;
        ArrayList<Computer> computerList = null;

        try (Connection con = connection.openConnection();
                PreparedStatement stmt = con.prepareStatement(DETAIL_REQUEST)) {
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            computerMapper = ComputerMapper.getInstance();
            computerList = (ArrayList<Computer>) computerMapper.map(rs);
            if (computerList.size() >= 1) {
                LOGGER.info("Found computer of id : " + id);
                return computerList.get(0);
            } else {
                LOGGER.warn("Couldn't find Computer of id : " + id);
                return null;
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * . update a computer detail in base
     *
     * @param id
     *            id of the computer to update
     * @param c
     *            Computer object containing fresh infos
     * @return 0 if Update fail, 1 if success
     */
    public int update(int id, Computer c) {
        LOGGER.debug("update Computer");

        int res = 0;
        try (Connection con = connection.openConnection();
                PreparedStatement stmt = con.prepareStatement(UPDATE_REQUEST)) {
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
            res = stmt.executeUpdate();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    @Override
    public Long count(String name) {
        LOGGER.debug("count computers");
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

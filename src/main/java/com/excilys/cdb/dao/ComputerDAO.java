package com.excilys.cdb.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.pagination.Page;

/**
 * Computer DAO, handle computer request.
 *
 * @see GenericDAO
 */
@Component("computerDAO")
public class ComputerDAO extends GenericDAO<Computer> {

    @Autowired
    @Qualifier("companyDAO")
    private CompanyDAO companyDAO;

    @Autowired
    @Qualifier("computerMapper")
    private ComputerMapper computerMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected EntityManager entityManager;

    public static final String INSERT_REQUEST = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
    public static final String DETAIL_REQUEST = "SELECT * FROM computer WHERE id=?";
    public static final String DELETE_REQUEST = "DELETE FROM computer WHERE id=?";
    public static final String UPDATE_REQUEST = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
    public static final String LISTALL_REQUEST = "SELECT * FROM computer;";
    public static final String LISTPAGE_REQUEST = "SELECT * FROM computer WHERE computer.name LIKE ? ORDER BY %s LIMIT ?,?";
    public static final String COUNT_REQUEST = "SELECT COUNT(*) FROM computer WHERE name LIKE ?";
    public static final String DELETE_ALL_REQUEST = "DELETE FROM computer WHERE company_id=?";
    static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    /**
     * ComputerDAO constructor.
     *
     * @param dataSource
     *            datasource instanciate by Spring
     */
    public ComputerDAO() {
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Remove a computer from base.
     *
     * @param id
     *            computer id to delete
     * @return int : number of row affected (0 or 1)
     */
    public int delete(int id) {
        LOGGER.debug("delete Computer");

        Object[] args = { id };
        int res = 0;
        try {
            res = jdbcTemplate.update(DELETE_REQUEST, args);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
        return res;
    }

    /**
     * Delete all computer from a given companyID.
     *
     * @param companyId
     *            if of company
     */
    public void deleteAll(int companyId) {
        LOGGER.debug("delete All Computer from a company ID");
        Object[] args = { companyId };
        try {
            jdbcTemplate.update(DELETE_ALL_REQUEST, args);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * Ask database for all Computer.
     *
     * @return ArrayList<Computer> all computer list
     */
    @Override
    public List<Computer> listAll() {
        LOGGER.debug("List all computer");
        List<Computer> listRes = new ArrayList<>();
        //List<Computer> listRes;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
            Root<Computer> from = criteriaQuery.from(Computer.class);
            
            CriteriaQuery<Object> select = criteriaQuery.select(from);
            TypedQuery<Object> typedQuery = entityManager.createQuery(select);
            List<Object> resultlist = typedQuery.getResultList();
            
            for(Object o:resultlist) {
                Computer c = (Computer)o;
                listRes.add(c);
             }
            
            //listRes = jdbcTemplate.query(LISTALL_REQUEST, computerMapper);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
        return listRes;
    }

    /**
     * Ask database for Computer by page.
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
        String request = null;

        if (!order.equals("")) {
            request = String.format(LISTPAGE_REQUEST, order);

        } else {
            request = String.format(LISTPAGE_REQUEST, "id");
        }

        Object[] args = { "%" + name + "%", start, offset };
        try {
            elementList = (ArrayList<Computer>) jdbcTemplate.query(request,
                    args, computerMapper);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }

        Page<Computer> page = new Page<>(elementList, start, offset);
        return page;
    }

    /**
     * Adding Computer to database.
     *
     * @param c
     *            computer to add
     * @return 0 if Insert fail, 1 if everything is fine
     */
    public Computer add(Computer c) {
        LOGGER.debug("adding Computer");
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("computer").usingGeneratedKeyColumns("id")
                .usingColumns("name", "introduced", "discontinued",
                        "company_id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", c.getName());
        parameters.put("introduced", c.getIntroduced() == null ? null
                : Date.valueOf(c.getIntroduced()));
        parameters.put("discontinued", c.getDiscontinued() == null ? null
                : Date.valueOf(c.getDiscontinued()));
        parameters.put("company_id",
                (c.getCompany() == null) ? null : c.getCompany().getId());
        try {
            Long id = insert.executeAndReturnKey(parameters).longValue();
            c.setId(id);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
        return c;
    }

    /**
     * Getting computer detail from base.
     *
     * @param id
     *            computer id to display
     * @return Computer
     */
    public Computer get(int id) {
        LOGGER.debug("getting computer detail");
        Computer c = null;
        try {
            c = jdbcTemplate.queryForObject(DETAIL_REQUEST, new Object[] { id },
                    computerMapper);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
        return c;
    }

    /**
     * update a computer detail in base.
     *
     * @param id
     *            id of the computer to update
     * @param c
     *            Computer object containing fresh infos
     * @return 0 if Update fail, 1 if success
     */
    public int update(int id, Computer c) {
        LOGGER.debug("update Computer");
        Object[] args = { c.getName(),
                (c.getIntroduced() == null) ? null
                        : Date.valueOf(c.getIntroduced()),
                (c.getDiscontinued() == null) ? null
                        : Date.valueOf(c.getDiscontinued()),
                (c.getCompany() == null) ? null : c.getCompany().getId(), id };
        int res = 0;
        try {
            res = jdbcTemplate.update(UPDATE_REQUEST, args);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
        return res;
    }

    @Override
    public Long count(String name) {
        LOGGER.debug("count computers");
        Long res = new Long(0);
        Object[] args = { name + "%" };
        try {
            res = jdbcTemplate.queryForObject(COUNT_REQUEST, args, Long.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
        return res;
    }
}

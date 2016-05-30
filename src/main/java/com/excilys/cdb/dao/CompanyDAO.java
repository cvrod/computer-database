package com.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
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

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.pagination.Page;

/**
 * CompanyDAO : handle company request.
 *
 * @see GenericDAO
 *
 */
@Component("companyDAO")
public class CompanyDAO extends GenericDAO<Company> {

    @Autowired
    @Qualifier("companyMapper")
    private CompanyMapper companyMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected EntityManager entityManager;

    protected CriteriaBuilder criteriaBuilder;
    protected CriteriaQuery<Company> criteriaQuery;
    protected Root<Company> companyRoot;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CompanyDAO.class);
    public static final String DETAIL_REQUEST = "SELECT * FROM company WHERE id=?";
    public static final String LISTALL_REQUEST = "SELECT * FROM company;";
    public static final String LISTPAGE_REQUEST = "SELECT * FROM company WHERE company.name LIKE ? ORDER BY %s LIMIT ?,?";
    public static final String DELETE_REQUEST = "DELETE FROM company WHERE id=?";
    public static final String INSERT_REQUEST = "INSERT INTO company (name) VALUES(?)";
    public static final String UPDATE_REQUEST = "UPDATE company SET name=? WHERE id=?";
    public static final String COUNT_REQUEST = "SELECT COUNT(*) FROM company WHERE name like ?";
    public static final String DELETE_COMPUTER = "DELETE FROM computer WHERE company_id=?";

    /**
     * CompanyDAO main constructor.
     *
     * @param dataSource
     *            dataSource instanciate by Spring
     */
    public CompanyDAO() {
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PostConstruct
    public void postConstruct() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(Company.class);
        companyRoot = criteriaQuery.from(Company.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Getting a company under ResultSet form.
     *
     * @param id
     *            Company id
     * @return ResultSet containing Company information
     */
    @Override
    public Company get(int id) {
        LOGGER.debug("getting a company by id");
        Company c = null;
        criteriaQuery.select(companyRoot).where(criteriaBuilder.equal(companyRoot.get("id"),id));
        TypedQuery<Company> typedQuery = entityManager
                .createQuery(criteriaQuery);
        c = typedQuery.getSingleResult();
        return c;
    }

    /**
     * Ask database for all Company.
     *
     * @return ArrayList<Company> all computer list
     */
    @Override
    public List<Company> listAll() {
        LOGGER.debug("List all company");
        List<Company> list = null;

        criteriaQuery.select(companyRoot);
        TypedQuery<Company> typedQuery = entityManager
                .createQuery(criteriaQuery);
        list = typedQuery.getResultList();
        return list;
    }

    /**
     * Ask database for Company by page.
     *
     * @return ArrayList<Company> company list
     */
    @Override
    public Page<Company> listAllByPage(String name, String order, int start,
            int offset) {
        if (start < 0 || offset < 0) {
            return null;
        }
        LOGGER.debug("List company by Page");

        ArrayList<Company> elementList = null;
        String request = null;

        if (order.equals("id") || order.equals("name")
                || order.equals("introduced") || order.equals("discontinued")
                || order.equals("company_id")) {
            request = String.format(LISTPAGE_REQUEST, order);
        } else {
            request = String.format(LISTPAGE_REQUEST, "id");
        }

        Object[] args = { "%" + name + "%", start, offset };
        try {
            elementList = (ArrayList<Company>) jdbcTemplate.query(request, args,
                    new CompanyMapper());
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
        Page<Company> page = new Page<>(elementList, start, offset);

        return page;
    }

    /**
     * Remove a company from base (using transaction).
     *
     * @param companyId
     *            id of company to delete company id to delete
     * @return int : number of row affected (0 or 1)
     */
    public int delete(int companyId) {
        LOGGER.debug("delete a Company");
        Object[] args = { companyId };
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
     * Adding Company to database.
     *
     * @param c
     *            company to add
     * @return 0 if Insert fail, 1 if everything is fine
     */
    @Override
    public Company add(Company c) {
        LOGGER.debug("adding Company");

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("company").usingGeneratedKeyColumns("id")
                .usingColumns("name");
        try {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("name", c.getName());
            Long id = insert.executeAndReturnKey(parameters).longValue();
            c.setId(id);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
        return c;
    }

    /**
     * update a company detail in base.
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
        Object[] args = { c.getName(), c.getId() };
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
        LOGGER.debug("count company");
        CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        cq.select(criteriaBuilder.count(cq.from(Company.class)));
        return entityManager.createQuery(cq).getSingleResult();
    }
}

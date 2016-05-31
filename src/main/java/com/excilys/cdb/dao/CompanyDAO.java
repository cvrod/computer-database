package com.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    protected EntityManager entityManager;

    protected CriteriaBuilder criteriaBuilder;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CompanyDAO.class);

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
    }

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
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

        CriteriaQuery<Company> criteriaQuery = criteriaBuilder
                .createQuery(Company.class);
        Root<Company> companyRoot = criteriaQuery.from(Company.class);

        criteriaQuery.select(companyRoot)
                .where(criteriaBuilder.equal(companyRoot.get("id"), id));
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

        CriteriaQuery<Company> criteriaQuery = criteriaBuilder
                .createQuery(Company.class);
        Root<Company> companyRoot = criteriaQuery.from(Company.class);

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

        CriteriaQuery<Company> criteriaQuery = criteriaBuilder
                .createQuery(Company.class);
        Root<Company> companyRoot = criteriaQuery.from(Company.class);

        criteriaQuery.select(companyRoot);
        criteriaQuery.where(criteriaBuilder.like(companyRoot.get("name"),
                "%" + name + "%"));
        if (order.equals("id") || order.equals("name")
                || order.equals("introduced") || order.equals("discontinued")
                || order.equals("company_id")) {
            criteriaQuery.orderBy(
                    criteriaBuilder.asc(companyRoot.get(order.split(" ")[0])));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(companyRoot.get("id")));
        }

        TypedQuery<Company> typedQuery = entityManager
                .createQuery(criteriaQuery).setFirstResult(start)
                .setMaxResults(offset);

        elementList = (ArrayList<Company>) typedQuery.getResultList();
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
        CriteriaDelete<Company> delete = criteriaBuilder
                .createCriteriaDelete(Company.class);
        Root<Company> e = delete.from(Company.class);
        delete.where(criteriaBuilder.equal(e.get("id"), companyId));
        return this.entityManager.createQuery(delete).executeUpdate();
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

        this.entityManager.persist(c);
        this.entityManager.flush();
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

        CriteriaUpdate<Company> criteriaUpdate = criteriaBuilder
                .createCriteriaUpdate(Company.class);
        Root<Company> companyRootUpdate = criteriaUpdate.from(Company.class);

        criteriaUpdate.set("name", c.getName());
        criteriaUpdate
                .where(criteriaBuilder.equal(companyRootUpdate.get("id"), id));
        return this.entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public Long count(String name) {
        LOGGER.debug("count company");
        CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        Root<Company> companyRoot = cq.from(Company.class);
        cq.select(criteriaBuilder.count(companyRoot)).where(criteriaBuilder
                .like(companyRoot.get("name"), "%" + name + "%"));

        return entityManager.createQuery(cq).getSingleResult();
    }
}

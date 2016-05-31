package com.excilys.cdb.dao;

import java.sql.Date;
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

    protected EntityManager entityManager;

    protected CriteriaBuilder criteriaBuilder;

    static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    /**
     * ComputerDAO constructor.
     *
     * @param dataSource
     *            datasource instanciate by Spring
     */
    public ComputerDAO() {

    }

    @PostConstruct
    public void postConstruct() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
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
        CriteriaDelete<Computer> delete = criteriaBuilder
                .createCriteriaDelete(Computer.class);
        Root<Computer> e = delete.from(Computer.class);
        delete.where(criteriaBuilder.equal(e.get("id"), id));
        return this.entityManager.createQuery(delete).executeUpdate();
    }

    /**
     * Delete all computer from a given companyID.
     *
     * @param companyId
     *            if of company
     */
    public void deleteAll(int companyId) {
        LOGGER.debug("delete All Computer from a company ID");
        CriteriaDelete<Computer> delete = criteriaBuilder
                .createCriteriaDelete(Computer.class);
        Root<Computer> e = delete.from(Computer.class);
        delete.where(criteriaBuilder.equal(e.get("company_id"), companyId));
        this.entityManager.createQuery(delete).executeUpdate();
    }

    /**
     * Ask database for all Computer.
     *
     * @return ArrayList<Computer> all computer list
     */
    @Override
    public List<Computer> listAll() {
        LOGGER.debug("List all computer");

        List<Computer> list = null;
        CriteriaQuery<Computer> criteriaQuery = criteriaBuilder
                .createQuery(Computer.class);
        Root<Computer> computerRoot = criteriaQuery.from(Computer.class);

        criteriaQuery.select(computerRoot);
        TypedQuery<Computer> typedQuery = entityManager
                .createQuery(criteriaQuery);
        list = typedQuery.getResultList();
        return list;
    }

    /**
     * Ask database for Computer by page.
     *
     * @return ArrayList<Computer> computer list
     */
    @Override
    public Page<Computer> listAllByPage(String name, String order, int start,
            int offset) {
        LOGGER.debug("List computer by Page");
        if (start < 0 || offset < 0) {
            return null;
        }
        ArrayList<Computer> elementList = null;

        CriteriaQuery<Computer> criteriaQuery = criteriaBuilder
                .createQuery(Computer.class);
        Root<Computer> computerRoot = criteriaQuery.from(Computer.class);

        criteriaQuery.select(computerRoot);
        criteriaQuery.where(criteriaBuilder.like(computerRoot.get("name"),
                "%" + name + "%"));
        if (!order.equals("")) {
            String [] orderSplit = order.split(" ");
            if(orderSplit.length == 1){
            criteriaQuery.orderBy(
                    criteriaBuilder.asc(computerRoot.get(order.split(" ")[0])));
            }
            else if (orderSplit.length == 2){
                if (orderSplit[1].equals("asc")){
                    criteriaQuery.orderBy(
                            criteriaBuilder.asc(computerRoot.get(order.split(" ")[0])));
                }
                else if (orderSplit[1].equals("desc")) {
                    criteriaQuery.orderBy(
                            criteriaBuilder.desc(computerRoot.get(order.split(" ")[0])));
                }
            }

        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(computerRoot.get("id")));
        }

        TypedQuery<Computer> typedQuery = entityManager
                .createQuery(criteriaQuery).setFirstResult(start)
                .setMaxResults(offset);

        elementList = (ArrayList<Computer>) typedQuery.getResultList();
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
        this.entityManager.persist(c);
        this.entityManager.flush();
        this.entityManager.clear();
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

        CriteriaQuery<Computer> criteriaQuery = criteriaBuilder
                .createQuery(Computer.class);
        Root<Computer> computerRoot = criteriaQuery.from(Computer.class);

        criteriaQuery.select(computerRoot)
                .where(criteriaBuilder.equal(computerRoot.get("id"), id));
        TypedQuery<Computer> typedQuery = entityManager
                .createQuery(criteriaQuery);
        c = typedQuery.getSingleResult();
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
        int res = 0;

        CriteriaUpdate<Computer> criteriaUpdate = criteriaBuilder
                .createCriteriaUpdate(Computer.class);
        Root<Computer> computerRootUpdate = criteriaUpdate.from(Computer.class);

        criteriaUpdate.set("name", c.getName());
        criteriaUpdate.set("introduced", (c.getIntroduced() == null) ? null
                : Date.valueOf(c.getIntroduced()));
        criteriaUpdate.set("discontinued", (c.getDiscontinued() == null) ? null
                : Date.valueOf(c.getDiscontinued()));
        criteriaUpdate.set("company",
                (c.getCompany() == null) ? null : c.getCompany().getId());
        criteriaUpdate
                .where(criteriaBuilder.equal(computerRootUpdate.get("id"), id));

        res = this.entityManager.createQuery(criteriaUpdate).executeUpdate();
        this.entityManager.clear();
        return res;
    }

    @Override
    public Long count(String name) {
        LOGGER.debug("count computers");
        CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        Root<Computer> computerRoot = cq.from(Computer.class);
        cq.select(criteriaBuilder.count(computerRoot)).where(criteriaBuilder
                .like(computerRoot.get("name"), "%" + name + "%"));

        return entityManager.createQuery(cq).getSingleResult();
    }
}

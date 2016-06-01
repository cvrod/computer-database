package com.excilys.cdb.dao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.User;

@Component("userDAO")
public class UserDAO {
    protected EntityManager entityManager;

    protected CriteriaBuilder criteriaBuilder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);

    /**
     * UserDAO main constructor.
     */
    public UserDAO() {
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void postConstruct() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    /**
     * getting user from login.
     * @param login user login
     * @return user from db
     */
    public User findByLogin(String login) {
        LOGGER.debug("getting User by login");
        CriteriaQuery<User> criteriaQuery = criteriaBuilder
                .createQuery(User.class);
        Root<User> companyRoot = criteriaQuery.from(User.class);

        criteriaQuery.select(companyRoot)
                .where(criteriaBuilder.equal(companyRoot.get("login"), login));
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    /**
     * adding user to db.
     * @param u user to add
     * @return fresh user from db
     */
    public User add(User u) {
        LOGGER.debug("adding user to db");
        this.entityManager.persist(u);
        this.entityManager.flush();
        return u;
    }

    /**
     * delete user from db.
     * @param userId user id to delete
     * @return 1 if success 0 else
     */
    public int delete(Long userId) {
        LOGGER.debug("delete a User");
        CriteriaDelete<User> delete = criteriaBuilder
                .createCriteriaDelete(User.class);
        Root<User> e = delete.from(User.class);
        delete.where(criteriaBuilder.equal(e.get("id"), userId));
        return this.entityManager.createQuery(delete).executeUpdate();
    }
}

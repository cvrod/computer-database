package com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.pagination.Page;
import com.excilys.cdb.persistence.ConnectionFactory;

/**
 * Main DAO class.
 */
public abstract class GenericDAO<T> {
    protected ConnectionFactory connection = null;
    CompanyMapper companyMapper = null;
    ComputerMapper computerMapper = null;
    public ResultSet setRes = null;
    StringBuffer res;
    static final Logger LOGGER = LoggerFactory.getLogger(GenericDAO.class);

    /**
     * Generic DAO constructor.
     */
    public GenericDAO() {
        connection = ConnectionFactory.getInstance();
    }

    /**
     * list all object from a given type.
     * @return list of all object in table
     */
    public abstract List<T> listAll();

    /**
     * list object by page.
     * @param name name to search
     * @param order column to order
     * @param start start index
     * @param offset page offset
     * @return Page object who contain list of needed object
     */
    public abstract Page<T> listAllByPage(String name, String order, int start, int offset);

    /**
     * delete an object from table.
     * @param id id of object to delete
     * @return 1 if delete OK, 0 else
     */
    public abstract int delete(int id);

    /**
     * add an object to DB.
     * @param c object to add
     * @return fresh now object
     */
    public abstract T add(T c);

    /**
     * return an object from a given id.
     * @param id id of object to return
     * @return Object from DB
     */
    public abstract T get(int id);

    /**
     * update an object from DB.
     * @param id id of object to update
     * @param c fresh object
     * @return 0 if update fail, 1 else
     */
    public abstract int update(int id, T c);

    /**
     * count the number of entities.
     * @param name name to search
     * @return number of entities
     */
    public abstract Long count(String name);

}

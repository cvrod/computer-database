package com.excilys.cdb.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Object company, contain a row from company table.
 */
public class Company implements Comparable<Company> {
    protected String name = null;
    protected Long id = null;
    static final Logger LOGGER = LoggerFactory.getLogger(Company.class);

    /**
     * Company constructor.
     */
    public Company() {
    };

    /**
     * Company constructor.
     *
     * @param id
     *            company id
     * @param name
     *            company name
     */
    public Company(Long id, String name) {
        LOGGER.debug("creating new Company Object with id : " + id);
        this.name = name;
        this.id = id;
    }

    /**
     * Company constructor.
     *
     * @param name
     *            company name
     */
    public Company(String name) {
        LOGGER.info("creating new Company without id");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int compareTo(Company o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Company)) {
            return false;
        }
        Company other = (Company) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**.
     * return str describing a Company
     * @return str Company
     */
    public String toString() {
        return "Company ID : " + this.id + ", Name : " + this.name;
    }

}

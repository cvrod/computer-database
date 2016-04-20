package com.excilys.cdb.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Object company, contain a row from company table
 */
public class Company implements Comparable<Company> {
	protected String name = null;
	protected Long id = null;
	final static Logger logger = LoggerFactory.getLogger(Company.class);

	public Company() {
	};

	/**
	 * Company constructor
	 * 
	 * @param id
	 *            company id
	 * @param name
	 *            company name
	 */
	public Company(Long id, String name) {
		logger.info("creating new Company");
		this.name = name;
		this.id = id;
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

	public boolean equals(Company o) {
		if (this == o) {
			return true;
		}

		if (o == null) {
			return false;
		}

		if (id != o.id) {
			return false;
		}

		if (name == null) {
			if (o.name != null) {
				return false;
			}
		} else if (!name.equals(o.name)) {
			return false;
		}

		return true;
	}

	public String toString() {
		return "Company ID : " + this.id + ", Name : " + this.name;
	}

}

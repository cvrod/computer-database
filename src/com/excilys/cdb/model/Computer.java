package com.excilys.cdb.model;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Computer object
 * Contain a row from computer table
 */
public class Computer implements Comparable<Computer> {
	private Long id = null;
	private String name = null;
	private LocalDate introduced = null;
	private LocalDate discontinued = null;
	private Company company = null;
	final static Logger logger = LoggerFactory.getLogger(Computer.class);

	public Computer() {
	};

	/**
	 * Contructor without ID
	 * 
	 * @param name
	 *            computer name
	 * @param introduction
	 *            introduction date (if exist)
	 * @param discontinued
	 *            discontinued date (if exist)
	 * @param comp
	 *            company name (if exist)
	 */
	public Computer(String name, String introduction, String discontinued, Company comp) {
		logger.info("creating new Computer");
		
		this.setName(name);
		if (introduction != null) {
			introduction = introduction.split(" ")[0];
			this.setIntroduced(LocalDate.parse(introduction));
		} else {
			introduction = null;
		}

		if (discontinued != null) {
			discontinued = discontinued.split(" ")[0];
			this.setDiscontinued(LocalDate.parse(discontinued));
		} else {
			discontinued = null;
		}
		if (comp != null) {
			this.setCompany(comp);
		} else {
			this.setCompany(null);
		}
	}

	/**
	 * Contructor with an ID
	 * 
	 * @param id
	 *            computer id
	 * @param name
	 *            computer name
	 * @param introduced
	 *            introduce date
	 * @param discontinued
	 *            discontinued date
	 * @param comp
	 *            company
	 */
	public Computer(Long id, String name, String introduced, String discontinued, Company comp) {
		logger.info("creating new Computer");
		
		this.id = id;
		this.name = name;

		if (introduced == null)
			this.introduced = null;
		else {
			introduced = introduced.split(" ")[0];
			this.introduced = LocalDate.parse(introduced);
		}

		if (discontinued == null)
			this.discontinued = null;
		else {
			discontinued = discontinued.split(" ")[0];
			this.discontinued = LocalDate.parse(discontinued);
		}
		this.setCompany(comp);
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int compareTo(Computer o) {
		if(this.id == o.getId()){
			return 0;
		}
		else if(this.id < o.getId()){
			return -1;
		}
		else {
			return 1;
		}
	}

	public String toString() {
		StringBuffer s = new StringBuffer(name);
		if (introduced != null){
			s.append(", introduced in ");
			s.append(this.introduced);
		}
		if (discontinued != null){
			s.append(", discontinued in ");
			s.append(this.discontinued);
		}
		if (getCompany() != null){
			s.append(", provided by ");
			s.append(this.getCompany().getName());
		}
		return s.toString();
	}
}

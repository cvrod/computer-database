package com.excilys.cdb.model;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Computer object
 * Contain a row from computer table
 */
public class Computer {
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
	
	public void setId(Long id){
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Computer))
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
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
	
	/**
	 * Builder pattern to create computer
	 */
	public static class Builder {
		private Long id = null;
		private String name = null;
		private String introduced = null;
		private String discontinued = null;
		private Company company = null;


		public Builder() {}

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder introduced(String intro) {
			this.introduced = intro;
			return this;
		}

		public Builder discontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public Builder company(Company company) {
			this.company = company;
			return this;
		}

		public Computer build() {
			return new Computer(id, name, introduced, discontinued, company);
		}
	}
}

package com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer implements Comparable<Computer> {
	private long id = 0;
	private String name = null;
	private LocalDate introduced = null;
	private LocalDate discontinued = null;
	private Company company = null;

	public Computer() {
	};

	/**
	 * Contructor without ID
	 * 
	 * @param name
	 * @param introduction
	 * @param discontinued
	 * @param comp
	 */
	public Computer(String name, String introduction, String discontinued, Company comp) {
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

	protected Company getCompany() {
		return company;
	}

	protected void setCompany(Company company) {
		this.company = company;
	}

	protected LocalDate getDiscontinued() {
		return discontinued;
	}

	protected void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	protected LocalDate getIntroduced() {
		return introduced;
	}

	protected void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected long getId() {
		return id;
	}

	@Override
	public int compareTo(Computer o) {
		if (this == o) {
			return 0;
		} else if (o == null) {
			return -1;
		} else if (!name.equals(o.getName())) {
			return -1;
		} else if (!introduced.equals(o.getIntroduced())) {
			return -1;
		} else if (!discontinued.equals(o.getDiscontinued())) {
			return -1;
		} else if (!company.equals(o.getCompany())) {
			return -1;
		}
		return 0;

	}
}

package com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer implements Comparable<Computer>{
	protected long id = 0;
	protected String name = null;
	protected LocalDate introduced = null;
	protected LocalDate discontinued = null;
	protected Company company = null;

	public Computer() { };
	
	
	/**
	 * Contructor without ID
	 * @param name
	 * @param introduction
	 * @param discontinued
	 * @param comp
	 */
	public Computer(String name, String introduction, String discontinued, Company comp) {
		this.name = name;
		
		if (introduction != null) {
			introduction = introduction.split(" ")[0];
			this.introduced = LocalDate.parse(introduction);
		} else {
			introduction = null;
		}

		if (discontinued != null) {
			discontinued = discontinued.split(" ")[0];
			this.discontinued = LocalDate.parse(discontinued);
		} else {
			discontinued = null;
		}
		if(comp != null){
			this.company = comp;
		}
		else {
			this.company = null;
		}
	}


	@Override
	public int compareTo(Computer o) {
		// TODO Auto-generated method stub
		return 0;
	}
}

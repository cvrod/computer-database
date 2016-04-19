package com.excilys.cdb.model;

public class Company implements Comparable<Company>{
	protected String name = null;
	protected long id = -1;
	
	public Company() { };
	
	public Company(long id, String name) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
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
	
	public String toString(){
		return this.name;
	}
	
	
}

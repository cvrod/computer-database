package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface Mapper<T> {
	public ArrayList<T> map(ResultSet rs);
}

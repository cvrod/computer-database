package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.util.List;

/**
 * Mapper interface
 * @param <T>
 */
public interface Mapper<T> {
	public List<T> map(ResultSet rs);
}

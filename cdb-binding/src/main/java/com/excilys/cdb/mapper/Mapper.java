package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.util.List;

/**
 * Mapper interface.
 *
 * @param <T>
 */
public interface Mapper<T> {
    /**
     * mapper from ResultSet.
     * @param rs ResultSet to map
     * @return List of Object create by mapping
     */
    List<T> map(ResultSet rs);
}

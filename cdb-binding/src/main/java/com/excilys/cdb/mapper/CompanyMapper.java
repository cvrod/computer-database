package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;

/**
 * Company mapper, who convert a given ResultSet to a List of Company.
 *
 * @see Mapper
 */
@Component("companyMapper")
public class CompanyMapper implements Mapper<Company>, RowMapper<Company> {
    public static final String ID = "id";
    public static final String NAME = "name";
    static final Logger LOGGER = LoggerFactory
            .getLogger(CompanyMapper.class);

    /**
     * Getting an ArrayList of Company from a ResultSet.
     *
     * @param setRes
     *            resultSet to convert
     * @return ArrayList<Company> containing all companies informations
     */
    @Override
    public List<Company> map(ResultSet setRes) {
        ArrayList<Company> res = new ArrayList<>();
        try {
            while (setRes.next()) {
                Company tmp = new Company(setRes.getLong(ID),
                        setRes.getString(NAME));
                res.add(tmp);
            }
        } catch (SQLException e) {
            LOGGER.error("Company table error !");
            throw new MapperException(e);
        }
        return res;
    }

    @Override
    public Company mapRow(ResultSet rs, int rowNumber) throws SQLException {
        return new Company(rs.getLong(ID), rs.getString(NAME));
    }
}

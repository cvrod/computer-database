package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * Computer mapper, who convert a given ResultSet to a List of Computer.
 *
 * @see Mapper
 */
@Component("computerMapper")
public class ComputerMapper implements Mapper<Computer>, RowMapper<Computer> {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String INTRODUCED = "introduced";
    public static final String DISCONTINUED = "discontinued";
    public static final String COMPANY_ID = "company_id";

    @Autowired
    @Qualifier("companyMapper")
    private CompanyMapper companyMapper;

    @Autowired
    @Qualifier("companyDAO")
    public CompanyDAO companyDAO;
    static final Logger LOGGER = LoggerFactory.getLogger(ComputerMapper.class);

    /**
     * Getting an ArrayList of Computer from a ResultSet.
     *
     * @param setRes
     *            resultSet to convert
     * @return ArrayList<Company> containing all computers informations
     */
    @Override
    public List<Computer> map(ResultSet setRes) {
        ArrayList<Computer> res = new ArrayList<>();
        Company c = null;
        Long id;
        String name;
        String introduced;
        String discontinued;
        int companyId;
        try {
            while (setRes.next()) {
                id = setRes.getLong(ID);
                name = setRes.getString(NAME);
                introduced = setRes.getString(INTRODUCED);
                discontinued = setRes.getString(DISCONTINUED);
                companyId = setRes.getInt(COMPANY_ID);
                if (companyId != 0) {
                    c = companyDAO.get(companyId);
                }
                Computer tmp = new Computer.Builder().id(id).name(name)
                        .introduced(introduced).discontinued(discontinued)
                        .company(c).build();
                res.add(tmp);
            }
        } catch (SQLException e) {
            LOGGER.error("Computer table error !");
            throw new MapperException(e);
        }
        return res;
    }

    @Override
    public Computer mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Company c = null;
        Long id = rs.getLong(ID);
        String name = rs.getString(NAME);
        String introduced = rs.getString(INTRODUCED);
        String discontinued = rs.getString(DISCONTINUED);
        int companyId = rs.getInt(COMPANY_ID);
        if (companyId != 0) {
            c = companyDAO.get(companyId);
        }
        return new Computer.Builder().id(id).name(name)
                .introduced(introduced).discontinued(discontinued).company(c)
                .build();
    }
}

package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.ConnectionFactory;

/**.
 * Company mapper, who convert a given ResultSet to a List of Company
 *
 * @see Mapper
 */
public class CompanyMapper implements Mapper<Company> {
    public static final String ID = "id";
    public static final String NAME = "name";
    public ConnectionFactory connection = null;

    static CompanyMapper instance = null;

    /**.
     * return instance of Singleton CompanyMapper
     * @return CompanyMapper
     */
    public static synchronized CompanyMapper getInstance() {
        if (instance == null) {
            instance = new CompanyMapper();
        }
        return instance;
    }

    /**.
     * CompanyMapper constructor
     */
    private CompanyMapper() {
        connection = ConnectionFactory.getInstance();
    }

    /**.
     * Getting an ArrayList of Company from a ResultSet
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
            e.printStackTrace();
            System.out.println("Company table error !");
        }
        return res;
    }
}

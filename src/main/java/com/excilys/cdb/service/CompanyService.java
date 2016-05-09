package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.pagination.Page;

public interface CompanyService {

    /**.
     * return a company from id
     * @param id id of company
     * @return Company
     */
    Company get(int id);

    /**.
     * add a Company to DB
     * @param comp company to add
     * @return Fresh Company
     */
    Company add(Company comp);

    /**.
     * remove a Company
     * @param id id of company to remove
     * @return return 0 if id not found 1 if delete success
     */
    int delete(int id);

    /**.
     * update a company
     * @param id id of company to update
     * @param c fresh company object
     * @return 0 if company dont existe, 1 else
     */
    int update(int id, Company c);

    /**.
     * list all Company
     * @return List of all company
     */
    List<Company> listAll();

    /**.
     * return a page of Company
     * @param start start index
     * @param offset page offset
     * @return Page object
     */
    Page<Company> listAllByPage(int start, int offset);

    /**.
     * search company function
     * @param search search pattern
     * @param order row ordering
     * @param start start index
     * @param offset page offset
     * @return Company Page
     */
    Page<Company> listByPage(String search, String order, int start, int offset);

    /**.
     * return total number of element in company table
     * @return number of elements
     */
    Long count();

    /**.
     * return total number of element maching name in company table
     * @param name name to match
     * @return number of element in table Company
     */
    Long count(String name);
}

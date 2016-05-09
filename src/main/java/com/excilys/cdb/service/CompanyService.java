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
    public Company get(int id);
    
    /**.
     * add a Company to DB
     * @param comp company to add
     * @return Fresh Company
     */
    public Company add(Company comp);
    
    /**.
     * remove a Company
     * @param id id of company to remove
     * @return return 0 if id not found 1 if delete success
     */
    public int delete(int id);
    
    /**.
     * update a company
     * @param id id of company to update
     * @param c fresh company object
     * @return 0 if company dont existe, 1 else
     */
    public int update(int id, Company c);
    
    /**.
     * list all Company
     * @return List of all company
     */
    public List<Company> listAll();
    
    /**.
     * return a page of Company
     * @param start start index
     * @param offset page offset
     * @return Page object
     */
    public Page<Company> listAllByPage(int start, int offset);
    
    /**.
     * search company function
     * @param search search pattern
     * @param order row ordering
     * @param start start index
     * @param offset page offset
     * @return Company Page
     */
    public Page<Company> listByPage(String search, String order, int start, int offset);
    
    /**.
     * return total number of element in company table
     * @return number of elements
     */
    public Long count();
    
    /**.
     * return total number of element maching name in company table
     * @param name name to match
     * @return number of element in table Company
     */
    public Long count(String name);
}

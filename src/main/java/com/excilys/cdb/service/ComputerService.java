package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.pagination.Page;

public interface ComputerService {

    /**.
     * getting a Computer from database
     * @param id if of computer to get
     * @return Computer Object
     */
    public Computer get(int id);

    /**.
     * add a computer to DB
     * @param comp computer to add
     * @return fresh Computer Object
     */
    public Computer add(Computer comp);

    /**.
     * remove a Computer from DB
     * @param id id of Computer to delete
     * @return 0 if computer not found, 1 else
     */
    public int delete(int id);

    /**.
     * update a Computer on DB
     * @param id id of computer to update
     * @param c Fresh Computer object
     * @return 0 if computer not found, 1 else
     */
    public int update(int id, Computer c);

    /**.
     * listAll Computer
     * @return ArrayList of all computer in DB
     */
    public List<Computer> listAll();

    /**.
     * list computer by page
     * @param start start index
     * @param offset page offset
     * @return Computer Page
     */
    public Page<Computer> listAllByPage(int start, int offset);

    /**.
     * search computer function
     * @param search search pattern
     * @param order row ordering
     * @param start start index
     * @param offset page offset
     * @return Computer Page
     */
    public Page<Computer> listByPage(String search, String order, int start, int offset);

    /**.
     * return total number of element in computer table
     * @return number of elements in table
     */
    public Long count();

    /**.
     * return total number of element maching name in computer table
     * @param name name to match
     * @return number of element in table Computer
     */
    public Long count(String name);
}

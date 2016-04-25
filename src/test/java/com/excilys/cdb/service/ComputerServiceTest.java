package com.excilys.cdb.service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.pagination.Page;

public class ComputerServiceTest {

    static ComputerService computerService = null;
    static Computer computerTest = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        computerService = ComputerService.getInstance();
        computerTest = new Computer(new Long(1), "MacBook Pro 15.4 inch", (String) null,
                (String) null, new Company(new Long(1), "Apple Inc."));
    }

    @Test
    public void testGetInstance() {
        assertNotNull("Null Instance", computerService);
        assertEquals("Wrong instance", ComputerService.getInstance(), computerService);
    }

    @Test
    public void testGet() {
        Computer c = computerService.get(1);
        assertEquals("problem on get", computerTest, c);
        c = computerService.get(200000);
        assertNull("Company not null", c);
    }

    @Test
    public void testAdd() {
        Computer c = new Computer("testComputer", (String) null,
                (String) null, new Company(new Long(1), "Apple Inc."));
        Computer testAddComputer = computerService.add(c);
        assertNotNull(testAddComputer);
        assertEquals(c.getName(), testAddComputer.getName());
        computerService.delete(testAddComputer.getId().intValue());
    }

    @Test
    public void testDelete() {
        Computer c = new Computer("testComputer", (String) null,
                (String) null, new Company(new Long(1), "Apple Inc."));
        Computer testAddComputer = computerService.add(c);

        int deleteResult = computerService.delete(testAddComputer.getId().intValue());

        assertEquals(deleteResult, 1);
        deleteResult = computerService.delete(-1);
        assertEquals(deleteResult, 0);
    }

    @Test
    public void testUpdate() {
        Computer c = new Computer("testComputer", (String) null,
                (String) null, new Company(new Long(1), "Apple Inc."));
        Computer testAddComputer = computerService.add(c);
        c.setName("NewName");
        
        int updateResult = computerService.update(testAddComputer.getId().intValue(), c);
        assertEquals(updateResult, 1);
        testAddComputer = computerService.get(testAddComputer.getId().intValue());
        assertEquals(testAddComputer.getName(), c.getName());

        computerService.delete(testAddComputer.getId().intValue());
    }

    @Test
    public void testListAll() {
        Computer c = new Computer("testComputer", (String) null,
                (String) null, new Company(new Long(1), "Apple Inc."));
        Computer testAddComputer = computerService.add(c);
        ArrayList<Computer> testList = computerService.listAll();
        
        assertTrue(testList.contains(testAddComputer));
        
        computerService.delete(testAddComputer.getId().intValue());
    }

    @Test
    public void testListAllByPage() {
        Page<Computer> pageList = computerService.listAllByPage(0, 20);
        assertEquals(pageList.getElementList().size(), 20);
        pageList = computerService.listAllByPage(-1, 0);
        assertNull(pageList);
    }

}

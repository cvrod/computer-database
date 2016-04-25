package com.excilys.cdb.service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import com.excilys.cdb.model.*;
import com.excilys.cdb.pagination.Page;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author excilys
 *
 */
public class CompanyServiceTest {
    
    static CompanyService companyService = null;
    static Company companyTest = null;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        companyService = CompanyService.getInstance();
        companyTest = new Company(new Long(1), "Apple Inc.");
    }

    /**
     * Test method for {@link com.excilys.cdb.service.CompanyService#getInstance()}.
     */
    @Test
    public void testGetInstance() {
        assertNotNull("Null Instance", companyService);
        assertEquals("Wrong instance", CompanyService.getInstance(), companyService);
    }

    /**
     * Test method for {@link com.excilys.cdb.service.CompanyService#get(int)}.
     */
    @Test
    public void testGet() {
        Company c = companyService.get(1);
        assertEquals("problem on get", companyTest, c);
        c = companyService.get(600);
        assertNull("Company not null", c);
    }

    /**
     * Test method for {@link com.excilys.cdb.service.CompanyService#add(com.excilys.cdb.model.Company)}.
     */
    @Test
    public void testAdd() {
        Company c = new Company("testCompany");
        Company testAddCompany = companyService.add(c);
        assertNotNull(testAddCompany);
        assertEquals(c.getName(), testAddCompany.getName());
        companyService.delete(testAddCompany.getId().intValue());
    }

    /**
     * Test method for {@link com.excilys.cdb.service.CompanyService#delete(int)}.
     */
    @Test
    public void testDelete() {
        Company c = new Company("testCompany");
        Company testAddCompany = companyService.add(c);

        int deleteResult = companyService.delete(testAddCompany.getId().intValue());

        assertEquals(deleteResult, 1);
        deleteResult = companyService.delete(-1);
        assertEquals(deleteResult, 0);
    }

    /**
     * Test method for {@link com.excilys.cdb.service.CompanyService#update(int, com.excilys.cdb.model.Company)}.
     */
    @Test
    public void testUpdate() {
        Company c = new Company("testCompany");
        Company testAddCompany = companyService.add(c);
        c.setName("NewName");

        int updateResult = companyService.update(testAddCompany.getId().intValue(), c);
        assertEquals(updateResult, 1);
        testAddCompany = companyService.get(testAddCompany.getId().intValue());
        assertEquals(testAddCompany.getName(), c.getName());

        companyService.delete(testAddCompany.getId().intValue());
    }

    /**
     * Test method for {@link com.excilys.cdb.service.CompanyService#listAll()}.
     */
    @Test
    public void testListAll() {
        Company c = new Company("testCompany");
        Company testAddCompany = companyService.add(c);
        ArrayList<Company> testList = companyService.listAll();
        
        assertTrue(testList.contains(testAddCompany));
        
        companyService.delete(testAddCompany.getId().intValue());
    }

    /**
     * Test method for {@link com.excilys.cdb.service.CompanyService#listAllByPage(int, int)}.
     */
    @Test
    public void testListAllByPage() {
        Page<Company> pageList = companyService.listAllByPage(0, 20);
        assertEquals(pageList.getElementList().size(), 20);
        pageList = companyService.listAllByPage(-1, 0);
        assertNull(pageList);
    }

}

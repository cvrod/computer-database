package com.excilys.cdb.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class CompanyTest {

    static Company companyTest = null;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        companyTest = new Company();
    }

    @Test
    public void testCompanyLongString() {
        Company company = null;
        assertNull(company);
        company = new Company(new Long(1), "test");
        assertNotNull(company);
    }

    @Test
    public void testGetName() {
        companyTest = new Company();
        assertNull(companyTest.getName());
        
        companyTest = new Company("companyTest");
        assertEquals(companyTest.getName(), "companyTest");
    }

    @Test
    public void testGetId() {
        companyTest = new Company();
        assertNull(companyTest.getId());
        
        companyTest = new Company(new Long(1), "companyTest");
        assertEquals(companyTest.getId(), new Long(1));
    }

    @Test
    public void testSetName() {
        companyTest = new Company("companyTest");
        assertEquals(companyTest.getName(), "companyTest");
        
        companyTest.setName("test");
        assertEquals(companyTest.getName(), "test");
    }

    @Test
    public void testSetId() {
        companyTest = new Company();
        assertNull(companyTest.getId());
        
        companyTest.setId(new Long(1));
        assertEquals(companyTest.getId(), new Long(1));
    }

    @Test
    public void testEqualsObject() {
        Company company1 = new Company(new Long(1), "test");
        Company company2 = new Company(new Long(1), "test");
        
        assertTrue(company1.equals(company2));
    }

}

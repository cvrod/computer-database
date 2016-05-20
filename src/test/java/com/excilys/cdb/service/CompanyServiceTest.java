package com.excilys.cdb.service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import com.excilys.cdb.model.*;
import com.excilys.cdb.pagination.Page;
import com.excilys.cdb.validator.ValidatorException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/applicationContext.xml" })
public class CompanyServiceTest {

    @Autowired
    @Qualifier("companyService")
    private CompanyService companyService;

    @Spy
    static Company companyTest = null;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test method for
     * {@link com.excilys.cdb.service.CompanyServiceImpl#get(int)}.
     */
    @Test
    public void testGet() {
        Mockito.when(companyTest.getId()).thenReturn(new Long(1));
        Mockito.when(companyTest.getName()).thenReturn("Apple Inc.");
        Company c = companyService.get(1);

        assertEquals("problem on get (Id)", companyTest.getId(), c.getId());
        assertEquals("problem on get (name)", companyTest.getName(),
                c.getName());
        c = companyService.get(600);
        assertNull("Company not null", c);
    }

    /**
     * Test method for
     * {@link com.excilys.cdb.service.CompanyServiceImpl#add(com.excilys.cdb.model.Company)}
     * .
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
     * Test method for
     * {@link com.excilys.cdb.service.CompanyServiceImpl#delete(int)}.
     */
    @Test
    public void testDelete() {
        Company c = new Company("testCompany2");
        Company testAddCompany = companyService.add(c);

        int deleteResult = companyService
                .delete(testAddCompany.getId().intValue());
        assertEquals(deleteResult, 1);
    }

    @Test(expected = ValidatorException.class)
    public void testExceptionDelete() {
        companyService.delete(-1);
    }

    /**
     * Test method for
     * {@link com.excilys.cdb.service.CompanyServiceImpl#update(int, com.excilys.cdb.model.Company)}
     * .
     */
    @Test
    public void testUpdate() {
        Company c = new Company("testCompany");
        Company testAddCompany = companyService.add(c);
        c.setName("NewName");

        int updateResult = companyService
                .update(testAddCompany.getId().intValue(), c);
        assertEquals(updateResult, 1);
        testAddCompany = companyService.get(testAddCompany.getId().intValue());
        assertEquals(testAddCompany.getName(), c.getName());

        companyService.delete(testAddCompany.getId().intValue());
    }

    /**
     * Test method for
     * {@link com.excilys.cdb.service.CompanyServiceImpl#listAll()}.
     */
    @Test
    public void testListAll() {
        Company c = new Company("testCompany");
        Company testAddCompany = companyService.add(c);
        ArrayList<Company> testList = (ArrayList<Company>) companyService
                .listAll();

        assertTrue(testList.contains(testAddCompany));

        companyService.delete(testAddCompany.getId().intValue());
    }

    /**
     * Test method for
     * {@link com.excilys.cdb.service.CompanyServiceImpl#listAllByPage(int, int)}
     * .
     */
    @Test
    public void testListAllByPage() {
        Page<Company> pageList = companyService.listAllByPage(0, 20);
        assertEquals(pageList.getElementList().size(), 20);
        pageList = companyService.listAllByPage(-1, 0);
        assertNull(pageList);
    }

}

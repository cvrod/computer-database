package com.excilys.cdb.service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.pagination.Page;
import com.excilys.cdb.validator.ValidatorException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/applicationContext.xml" })
public class ComputerServiceTest {

    @Autowired
    @Qualifier("computerService")
    private ComputerService computerService;

    @Spy
    static Computer computerTest = null;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        computerTest = new Computer(new Long(1), "MacBook Pro 15.4 inch",
                (String) null, (String) null,
                new Company(new Long(1), "Apple Inc."));
    }

    @Test
    public void testGet() {
        Company companyTest = new Company(new Long(1), "Apple Inc.");

        Mockito.when(computerTest.getId()).thenReturn(new Long(1));
        Mockito.when(computerTest.getName())
                .thenReturn("MacBook Pro 15.4 inch");
        Mockito.when(computerTest.getCompany()).thenReturn(companyTest);

        Computer c = computerService.get(1);

        assertEquals("problem on get (Id)", computerTest.getId(), c.getId());
        assertEquals("problem on get (name)", computerTest.getName(),
                c.getName());
        assertEquals("problem on get (intro)", computerTest.getIntroduced(),
                c.getIntroduced());
        assertEquals("problem on get (discon)", computerTest.getDiscontinued(),
                c.getDiscontinued());
        assertEquals("problem on get (Company)", computerTest.getCompany(),
                c.getCompany());
    }

    @Test
    public void testAdd() {
        Computer c = new Computer("testComputer", (String) null, (String) null,
                new Company(new Long(1), "Apple Inc."));
        Computer testAddComputer = computerService.add(c);
        assertNotNull(testAddComputer);
        assertEquals(c.getName(), testAddComputer.getName());
        computerService.delete(testAddComputer.getId().intValue());
    }

    @Test
    public void testDelete() {
        Computer c = new Computer("testComputer", (String) null, (String) null,
                new Company(new Long(1), "Apple Inc."));
        Computer testAddComputer = computerService.add(c);

        int deleteResult = computerService
                .delete(testAddComputer.getId().intValue());

        assertEquals(deleteResult, 1);
    }

    @Test(expected = ValidatorException.class)
    public void testExceptionDelete() {
        computerService.delete(-1);
    }

    @Test
    public void testUpdate() {
        Computer c = new Computer("testComputer", (String) null, (String) null,
                new Company(new Long(1), "Apple Inc."));
        Computer testAddComputer = computerService.add(c);
        c.setName("NewName");

        int updateResult = computerService
                .update(testAddComputer.getId().intValue(), c);
        assertEquals(updateResult, 1);
        testAddComputer = computerService
                .get(testAddComputer.getId().intValue());
        assertEquals(testAddComputer.getName(), c.getName());

        computerService.delete(testAddComputer.getId().intValue());
    }

    @Test
    public void testListAll() {
        Computer c = new Computer("testComputer", (String) null, (String) null,
                new Company(new Long(1), "Apple Inc."));
        Computer testAddComputer = computerService.add(c);
        ArrayList<Computer> testList = (ArrayList<Computer>) computerService
                .listAll();

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

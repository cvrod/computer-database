package com.excilys.cdb.model;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ComputerTest {

    static Computer computerTest = null;
    
    @Spy
    Company companyMock =  new Company();
    
    Company companyTest =  new Company(new Long(1), "testCompany");
    
    Long companyId = new Long(1);
        
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        computerTest = new Computer();
    }
    
    @Test
    public void testComputerStringStringStringCompany() {
        Mockito.when(companyMock.getId()).thenReturn(new Long(1));
        Mockito.when(companyMock.getName()).thenReturn("testCompany");
        
        computerTest = new Computer("testName", "1980-05-01", "1984-04-01", companyTest);
        assertNull(computerTest.getId());
        assertEquals(computerTest.getName(), "testName");
        LocalDate introDate = LocalDate.parse("1980-05-01");
        assertEquals(computerTest.getIntroduced(), introDate);
        LocalDate discontinuedDate = LocalDate.parse("1984-04-01");
        assertEquals(computerTest.getDiscontinued(), discontinuedDate);
        
        assertEquals(computerTest.getCompany().getName(), companyMock.getName());
        assertEquals(computerTest.getCompany().getId(), companyMock.getId());
    }

    @Test
    public void testComputerLongStringStringStringCompany() {        
        computerTest = new Computer(new Long(1), "testName", "1980-05-01", "1984-04-01", companyTest);
        assertEquals(computerTest.getId(), new Long(1));
        computerTest = new Computer(null, "testName", "1980-05-01", "1984-04-01", companyTest);
        assertNull(computerTest.getId());
        computerTest = new Computer(null, "testName", null, "1984-04-01", companyTest);
        assertNull(computerTest.getIntroduced());
    }

    @Test
    public void testGetCompany() {
        computerTest = new Computer(new Long(1), "testName", "1980-05-01", "1984-04-01", companyTest);
        assertEquals(computerTest.getCompany(), new Company(new Long(1), "testCompany"));
    }

    @Test
    public void testSetCompany() {
        Mockito.when(companyMock.getId()).thenReturn(new Long(1));
        Mockito.when(companyMock.getName()).thenReturn("testCompany");
        computerTest = new Computer(new Long(1), "testName", "1980-05-01", "1984-04-01", null);
        assertNull(computerTest.getCompany());
        
        computerTest.setCompany(companyMock);
        assertEquals(companyMock.getId(), computerTest.getCompany().getId());
        assertEquals(companyMock.getName(), computerTest.getCompany().getName());
    }

    @Test
    public void testGetDiscontinued() {
        computerTest = new Computer();
        assertNull(computerTest.getDiscontinued());
        computerTest = new Computer(new Long(1), "testName", "1980-05-01", "1984-04-01", companyTest);
        assertEquals(computerTest.getDiscontinued(), LocalDate.parse("1984-04-01"));
    }

    @Test
    public void testSetDiscontinued() {
        computerTest = new Computer();
        LocalDate tmpDate = LocalDate.parse("1984-04-01");
        assertNull(computerTest.getDiscontinued());
        computerTest.setDiscontinued(tmpDate);
        assertEquals(computerTest.getDiscontinued(), tmpDate);
    }

    @Test
    public void testGetIntroduced() {
        computerTest = new Computer();
        assertNull(computerTest.getIntroduced());
        computerTest = new Computer(new Long(1), "testName", "1980-05-01", "1984-04-01", companyTest);
        assertEquals(computerTest.getIntroduced(), LocalDate.parse("1980-05-01"));
    }

    @Test
    public void testSetIntroduced() {
        computerTest = new Computer();
        LocalDate tmpDate = LocalDate.parse("1980-05-01");
        assertNull(computerTest.getIntroduced());
        computerTest.setIntroduced(tmpDate);
        assertEquals(computerTest.getIntroduced(), tmpDate);
    }

    @Test
    public void testGetName() {
        computerTest = new Computer();
        assertNull(computerTest.getName());
        
        computerTest = new Computer.Builder().name("test").build();
        assertEquals(computerTest.getName(), "test");
    }

    @Test
    public void testSetName() {
        computerTest = new Computer();
        String tmpName = "tmpName";
        assertNull(computerTest.getName());
        computerTest.setName(tmpName);
        assertEquals(computerTest.getName(), tmpName);
    }

    @Test
    public void testGetId() {
        computerTest = new Computer();
        assertNull(computerTest.getId());
        
        computerTest = new Computer.Builder().id(new Long(1)).build();
        assertEquals(computerTest.getId(), new Long(1));
    }

    @Test
    public void testSetId() {
        computerTest = new Computer();
        Long tmpId = new Long(1);
        assertNull(computerTest.getId());
        computerTest.setId(tmpId);
        assertEquals(computerTest.getId(), tmpId);
    }

    @Test
    public void testEqualsObject() {
        Computer computer1 = new Computer.Builder().name("computer").company(companyMock).build();
        Computer computer2 = new Computer.Builder().name("computer").company(companyMock).build();
        
        assertEquals(computer1, computer2);
        assertTrue(computer1.equals(computer2));
    }

}

package com.excilys.cdb.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.cdb.model.role.UserRole;

public class UserTest {
    
    static User userTest = null;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        userTest = new User();
    }
    
    @Test
    public void testSetLogin() {
      userTest.setLogin("testLogin");
      assertEquals(userTest.getLogin(), "testLogin");
    }
    
    @Test
    public void testSetPassword() {
      userTest.setPassword("testPassword");
      assertEquals(userTest.getPassword(), "testPassword");
    }
    
    @Test
    public void testSetUserRole() {
      userTest.setRole(UserRole.ADMIN_ROLE);
      assertEquals(userTest.getRole(), UserRole.ADMIN_ROLE);
    }
    
    @Test
    public void testConstructor() {
        User userTest = new User("login", "password", UserRole.ADMIN_ROLE);
        assertNull(userTest.getId());
        assertEquals(userTest.getLogin(), "login");
        assertEquals(userTest.getPassword(), "password");
        assertEquals(userTest.getRole(), UserRole.ADMIN_ROLE);
    }
    
}

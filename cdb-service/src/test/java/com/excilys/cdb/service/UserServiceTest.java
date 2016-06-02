package com.excilys.cdb.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.model.User;
import com.excilys.cdb.model.role.UserRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/applicationContext.xml" })
public class UserServiceTest {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Test
    public void testAdd() {
        User user = new User("testLogin", "testPassword", UserRole.ROLE_ADMIN);
        User userDb = userService.add(user);
        assertNotNull(userDb);
        assertEquals(user.getLogin(), userDb.getLogin());
        assertEquals(user.getPassword(), userDb.getPassword());
        assertEquals(user, userDb);
        userService.delete(userDb.getId());
    }

    @Test
    public void testGet() {
        User user = new User("testLogin", "testPassword", UserRole.ROLE_ADMIN);
        userService.add(user);
        User userDb = userService.getByLogin("testLogin");
        assertNotNull(userDb);
        assertEquals(user.getLogin(), userDb.getLogin());
        assertEquals(user.getPassword(), userDb.getPassword());
        userService.delete(userDb.getId());
    }
}

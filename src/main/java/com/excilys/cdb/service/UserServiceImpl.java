package com.excilys.cdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userDAO")
    private UserDAO userDAO;

    @Override
    public User getByLogin(String login) {
        return userDAO.findByLogin(login);
    }

    @Override
    public User add(User user) {
        return userDAO.add(user);
    }

    @Override
    public int delete(Long id) {
        return userDAO.delete(id);
    }

}

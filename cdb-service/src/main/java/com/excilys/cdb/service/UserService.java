package com.excilys.cdb.service;

import com.excilys.cdb.model.User;

public interface UserService {

    /**
     * return a user from login.
     * @param login user login
     * @return User
     */
    User getByLogin(String login);

    /**
     * add a user to DB.
     * @param user user to add
     * @return fresh User Object
     */
    User add(User user);

    /**
     * remove a user from DB.
     * @param id id of user to delete
     * @return 0 if user not found, 1 else
     */
    int delete(Long id);
}

package com.rideshare.service;

import com.rideshare.database.UserDAO;

public class UserService {

    UserDAO userDAO = new UserDAO();

    public boolean login(String username,
                         String password) {

        return userDAO.validateUser(
                username,
                password
        );
    }

    public boolean register(com.rideshare.model.User user) {
        return userDAO.createUser(user);
    }
}

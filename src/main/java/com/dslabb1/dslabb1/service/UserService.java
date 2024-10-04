package com.dslabb1.dslabb1.service;

import com.dslabb1.dslabb1.dao.UserDAO;
import com.dslabb1.dslabb1.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User authenticate(String username, String password) throws SQLException {
        User user = userDAO.getUserByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public void registerUser(String username, String password) throws SQLException {
        userDAO.saveUser(username, password);
    }
}

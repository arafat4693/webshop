package com.dslabb1.dslabb1.service;

import com.dslabb1.dslabb1.dao.UserDAO;
import com.dslabb1.dslabb1.controller.UserInfo;
import com.dslabb1.dslabb1.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserInfo authenticate(String username, String password) throws SQLException {
        User user = userDAO.getUserByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return convertToUserInfo(user);
        }
        return null;
    }

    private UserInfo convertToUserInfo(User user) {
        return new UserInfo(user.getId(), user.getUsername(), user.getRole());
    }

    public void registerUser(String username, String password) throws SQLException {
        userDAO.saveUser(username, password);
    }
}

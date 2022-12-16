package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDTO;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User findByUsername(String username);

    List<User> findAllRegisteredUsers();

    List<UserDTO> findAllExceptCurrentUser(int userId);

    int findIdByUsername(String username);

    boolean create(String username, String password);
}

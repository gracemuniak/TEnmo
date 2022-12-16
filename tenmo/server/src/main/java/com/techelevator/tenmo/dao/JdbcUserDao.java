package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private Account account = new Account();

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAllRegisteredUsers() {
        List<User> registeredUsers = new ArrayList<>();
        String sql = "SELECT * FROM account WHERE user_id != ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            User registeredUser = mapRowToUser(results);
            registeredUsers.add(registeredUser);
        }
        return registeredUsers;
    }

    @Override
    public List<UserDTO> findAllExceptCurrentUser(int userId) {
        List<UserDTO> registeredUsers = new ArrayList<>();
        String sql = "SELECT * FROM tenmo_user WHERE user_id != ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            UserDTO registeredUser = mapRowToUserDTO(results);
            registeredUsers.add(registeredUser);
        }
        return registeredUsers;
    }

    @Override
    public int findIdByUsername(String username) {
        String sql = "SELECT user_id FROM tenmo_user WHERE username ILIKE ?;";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if (id != null) {
            return id;
        } else {
            return -1;
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, username, password_hash FROM tenmo_user;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }
        return users;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT user_id, username, password_hash FROM tenmo_user WHERE username ILIKE ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if (rowSet.next()) {
            return mapRowToUser(rowSet);
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean create(String username, String password) {


        // create user
        String sql = "INSERT INTO tenmo_user (username, password_hash) VALUES (?, ?) RETURNING user_id";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        Integer newUserId;
        try {
            newUserId = jdbcTemplate.queryForObject(sql, Integer.class, username, password_hash);
        } catch (DataAccessException e) {
            return false;
        }
        String sqlAccount = "INSERT INTO account (user_id, balance) VALUES (?,?) RETURNING account_id";
        Integer newAccountId;
        try {
            newAccountId = jdbcTemplate.queryForObject(sqlAccount, Integer.class, newUserId, account.getBalance());
        } catch (RestClientResponseException e) {
            return false;
        }
        return true;
    }
        private User mapRowToUser (SqlRowSet rs){
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password_hash"));
            user.setActivated(true);
            user.setAuthorities("USER");
            return user;
        }
    private UserDTO mapRowToUserDTO (SqlRowSet rs){
        UserDTO user = new UserDTO();
        user.setUserId(rs.getInt("user_id"));
        user.setName(rs.getString("username"));
        return user;
    }
    }

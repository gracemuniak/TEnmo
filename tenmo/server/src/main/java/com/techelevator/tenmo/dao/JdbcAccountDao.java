package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Account account = mapToRowAccount(results);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public Account findByUserId(int userId) {
        Account account = null;
        String sql = "SELECT * FROM account WHERE user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        if (rowSet.next()){
            return mapToRowAccount(rowSet);
        } //exception to not return null????
        return account;
    }

    @Override
    public boolean create(int userId, BigDecimal balance) {
//      userid = id (from user)
        //set balance to 1000
        //account id will be autocreated based upon user id with the balance of 1000
        return false;
    }

    private Account mapToRowAccount(SqlRowSet response) {
        Account account = new Account();
        account.setAccountId(response.getInt("account_id"));
        account.setUserId(response.getInt("user_id"));
        account.setBalance(response.getBigDecimal("balance"));
        return account;
    }
}

package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.data.annotation.Id;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int getAccountId(int userId){
        int accountId = 0;
        String sql = "SELECT account_id FROM account WHERE user_id = ?";
        accountId = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return accountId;
    }
    @Override
    public BigDecimal getBalance(int accountId) {
        BigDecimal balance = BigDecimal.ZERO;
        String sql = "SELECT * FROM account WHERE account_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, accountId);
        if(rowSet.next()){
            balance = mapToRowAccount(rowSet).getBalance();
        }
        return balance;
    }


    private Account mapToRowAccount(SqlRowSet response) {
        Account account = new Account();
        account.setAccountId(response.getInt("account_id"));
        account.setUserId(response.getInt("user_id"));
        account.setBalance(response.getBigDecimal("balance"));
        return account;
    }


}

package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int transferFunds(BigDecimal amount, int userFrom, int userTo) {
        BigDecimal transferAmount = BigDecimal.ZERO;
        String sql = "INSERT INTO transfer (user_from, user_to, amount, transfer_status) VALUES (?, ?, ?, ?) ";

        return 0;
    }

    @Override
    public boolean fundsAvailable(BigDecimal amount, BigDecimal balance) {
        return false;
    }

    @Override
    public BigDecimal receiveBalance(BigDecimal amount, int userTo) {
        return null;
    }

    @Override
    public BigDecimal transferFunds() {
        return null;
    }
    @Override
    public Transfer createTransfer(BigDecimal amount, int userFrom, int userTo) {
        String sql = "INSERT INTO transfer (user_from, user_to, amount, transfer_status) VALUES (?, ?, ?, ?) " +
                "RETURNING transfer_id ";
        Transfer newTransfer = null;
        amount = newTransfer.getAmount();
        userFrom = newTransfer.getUserFrom();
        userTo = newTransfer.getUserTo();
        Integer newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, userFrom, userTo );

        return newTransfer;
    }



    private Transfer mapToRowTransfer(SqlRowSet response) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(response.getInt("transfer_id"));
        transfer.setUserFrom(response.getInt("user_from"));
        transfer.setUserTo(response.getInt("transfer_to"));
        transfer.setAmount(response.getBigDecimal("amount"));
        transfer.setTransferStatus(response.getString("transfer_status"));
        return transfer;
    }
}

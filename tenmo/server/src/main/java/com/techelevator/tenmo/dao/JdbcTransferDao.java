package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
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

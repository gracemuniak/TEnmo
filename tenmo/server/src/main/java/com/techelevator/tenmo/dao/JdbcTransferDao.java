package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTransferDao implements TransferDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
    public Transfer createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer (user_from, user_to, amount, transfer_status) VALUES (?, ?, ?, 'Pending') " +
                "RETURNING transfer_id ";
//        Transfer newTransfer = null;
        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getUserFrom(), transfer.getUserTo(), transfer.getAmount());
//        newTransfer.setAmount(amount);
//        newTransfer.setUserFrom(userFrom);
//        newTransfer.setUserTo(userTo);
//        newTransfer.setTransferId(sql);
//        Integer newTransferId = jdbcTemplate.queryForObject(sql, Integer.class);
        transfer.setTransferId(transferId);
        return transfer;
    }

    @Override
    public Transfer makeNewTransfer(Transfer transfer) {

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

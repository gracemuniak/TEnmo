package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

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
    public List<Transfer> listUsersTransfers(int id, int id1 ){
        List<Transfer> transferList = new ArrayList<>();
        String sql = "SELECT * FROM transfer WHERE user_to = ? OR user_from = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id, id1);
        while (results.next()){
            Transfer transfer = mapToRowTransfer(results);
            transferList.add(transfer);
        }
        return transferList;
    }

    @Override
    public List<Transfer> friendTransfers(int id) {
        List<Transfer> friendTransferList = new ArrayList<>();
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        while (results.next()){
            Transfer transfer = mapToRowTransfer(results);
            friendTransferList.add(transfer);
        }

        return friendTransferList;
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
        String sql = "INSERT INTO transfer (user_from, user_to, amount, transfer_status) VALUES (?, ?, ?, ?)" +
                "RETURNING transfer_id ";
        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getUserFrom(),
                transfer.getUserTo(), transfer.getAmount(), transfer.getTransferStatus());
        transfer.setTransferId(transferId);
        BigDecimal amount = transfer.getAmount();
        String sqlBalance = "SELECT balance FROM account WHERE user_id = ?";
        BigDecimal balance = jdbcTemplate.queryForObject(sqlBalance, BigDecimal.class, transfer.getUserFrom());
        if (balance.compareTo(amount) == 1) {

            return transfer;
        } else
        return null;
    }
    @Override
    public Transfer makeNewTransfer(Transfer transfer) {
        String sqlGetFromBalance = "SELECT balance FROM account WHERE user_id = ?";
        BigDecimal balanceFromDB = jdbcTemplate.queryForObject(sqlGetFromBalance, BigDecimal.class, transfer.getUserFrom());
        BigDecimal fromBalance = new BigDecimal(String.valueOf(balanceFromDB.subtract(transfer.getAmount())));
        String sqlGetToBalance = "SELECT balance FROM account WHERE user_id = ?";
        BigDecimal balanceTo = jdbcTemplate.queryForObject(sqlGetToBalance, BigDecimal.class, transfer.getUserTo());
        BigDecimal toBalance = new BigDecimal((String.valueOf(balanceTo.add(transfer.getAmount()))));
        System.out.println(fromBalance.compareTo(BigDecimal.valueOf(0)));
        if(fromBalance.compareTo(BigDecimal.valueOf(0)) == 1) {
            String sqlTransferTO = "UPDATE account SET balance = ? WHERE user_id = ?";
            jdbcTemplate.update(sqlTransferTO, fromBalance, transfer.getUserFrom());
            String sqlTransferFrom = "UPDATE account SET balance = ? WHERE user_id = ?";
            jdbcTemplate.update(sqlTransferFrom, toBalance, transfer.getUserTo());

            transfer.setTransferStatus("Approved");
            createTransfer(transfer);
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You don't have enough money!");
        }

        return transfer;
    }


    private Transfer mapToRowTransfer(SqlRowSet response) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(response.getInt("transfer_id"));
        transfer.setUserFrom(response.getInt("user_from"));
        transfer.setUserTo(response.getInt("user_to"));
        transfer.setAmount(response.getBigDecimal("amount"));
        transfer.setTransferStatus(response.getString("transfer_status"));
        return transfer;
    }
}

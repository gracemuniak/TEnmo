package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {


    int transferFunds(BigDecimal amount, int userFrom, int userTo); // userTo

    boolean fundsAvailable(BigDecimal amount, BigDecimal balance);

    BigDecimal receiveBalance(BigDecimal amount, int userTo);

    BigDecimal transferFunds();
}

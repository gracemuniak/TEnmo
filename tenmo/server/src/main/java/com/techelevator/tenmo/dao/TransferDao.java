package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer createTransfer(Transfer transfer);

    Transfer makeNewTransfer(Transfer transfer);

    boolean fundsAvailable(BigDecimal amount, BigDecimal balance);

    BigDecimal receiveBalance(BigDecimal amount, int userTo);

}

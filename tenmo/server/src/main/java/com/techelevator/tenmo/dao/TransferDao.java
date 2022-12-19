package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    List<Transfer> listUsersTransfers(int id, int id1);

    List<Transfer> friendTransfers(int id);

    Transfer createTransfer(Transfer transfer);

    Transfer makeNewTransfer(Transfer transfer);
}

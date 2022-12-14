package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    List<Account> findAll();

    Account findByUserId(int userId);

    boolean create(int userId);

}

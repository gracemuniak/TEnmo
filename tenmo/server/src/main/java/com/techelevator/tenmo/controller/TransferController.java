package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private UserDao userDao;
    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferController(UserDao userDao, TransferDao transferDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }
    @RequestMapping(value = "/transfer/userlist", method = RequestMethod.GET)
    public List<User> findAllRegisteredUsers() {
        return userDao.findAllRegisteredUsers();
    }
    @RequestMapping(value = "/transfer/amount", method = RequestMethod.POST)
    public BigDecimal transferFunds() {
        return transferDao.transferFunds();
    }



}

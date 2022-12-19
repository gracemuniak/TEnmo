package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private UserDao userDao;
    private TransferDao transferDao;
    private AccountDao accountDao;
    private Transfer transfer = new Transfer();

    public TransferController(UserDao userDao, TransferDao transferDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }
    @RequestMapping(value = "/transfer/userlist", method = RequestMethod.GET)
    public List<UserDTO> findAllRegisteredUsers(Principal principal) {
        return userDao.findAllExceptCurrentUser(userDao.findIdByUsername(principal.getName()));
    }


//    @RequestMapping(value = "/transfer/amount", method = RequestMethod.POST)
//    public Transfer createTransfer(@RequestBody Transfer transfer) {
//        return transferDao.createTransfer(transfer);
//    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/transfer/amount", method = RequestMethod.POST)
    public Transfer makeNewTransfer(@RequestBody Transfer transfer) {
        return transferDao.makeNewTransfer(transfer);
    }


}

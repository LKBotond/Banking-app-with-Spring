package com.banking.backend.services.account;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.banking.backend.dao.accounts.AccountDAO;
import com.banking.backend.dao.deletion.DeletionDao;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountDAO accountDAO;
    private final DeletionDao deletionDao;

    public void createAccount(Long userID) {
        accountDAO.create(userID);
    }

    public void deleteAccount(Long accountID) {
        deletionDao.deleteAccount(accountID);
    }

    public BigDecimal getCurrentBalance(Long accountId) {
        return accountDAO.getFundsForAccount(accountId);
    }
}

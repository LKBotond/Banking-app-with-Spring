package com.banking.backend.services.transactions.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.backend.dao.accounts.AccountDAO;
import com.banking.backend.dao.masterRecord.MasterRecordDao;
import com.banking.backend.services.transactions.TransactionService;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    AccountDAO accountDAO;
    MasterRecordDao masterRecordDao;

    public TransactionServiceImpl(AccountDAO accountDAO, MasterRecordDao masterRecordDao) {
        this.accountDAO = accountDAO;
        this.masterRecordDao = masterRecordDao;
    }

    @Override
    public boolean addToAccount(double funds, long accountID) {
        Double fundsOnRecord = accountDAO.getFundsForAccount(accountID);
        if (fundsOnRecord == null) {
            return false;
        }
        double sum = fundsOnRecord + funds;
        accountDAO.updateFundsForAccount(sum, accountID);
        return true;
    }

    @Override
    public boolean subtractFromAccount(double funds, long accountID) {
        Double fundsOnRecord = accountDAO.getFundsForAccount(accountID);
        if (fundsOnRecord == null) {
            return false;
        }
        double remainingBalance = fundsOnRecord - funds;
        if (remainingBalance < 0) {
            return false;
        }
        accountDAO.updateFundsForAccount(remainingBalance, accountID);
        return true;
    }

    @Override
    public boolean transaction(double funds, long sender, long receiver) {
        return false;
    }
}

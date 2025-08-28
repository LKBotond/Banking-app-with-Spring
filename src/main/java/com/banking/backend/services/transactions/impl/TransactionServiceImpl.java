package com.banking.backend.services.transactions.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.backend.dao.AccountDAO;
import com.banking.backend.dao.MasterRecordDao;
import com.banking.backend.services.transactions.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
    AccountDAO accountDAO;
    MasterRecordDao masterRecordDao;

    public TransactionServiceImpl(AccountDAO accountDAO, MasterRecordDao masterRecordDao){
        this.accountDAO=accountDAO;
        this.masterRecordDao=masterRecordDao;
    }

    @Override
    @Transactional
    public boolean addToAccount(double funds, long accountID) {
        double funds=accountDAO.
        return false;
    }

    @Override
    public boolean subtractFromAccount(double funds, long accountID) {
        return false;
    }

    @Override
    @Transactional
    public boolean transaction(double funds, long sender, long receiver) {
        return false;
    }
}

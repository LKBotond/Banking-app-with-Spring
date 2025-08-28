package com.banking.backend.services.transactions.impl;

import org.springframework.stereotype.Service;

import com.banking.backend.services.transactions.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Override
    public boolean addToAccount(double funds, long accountID) {
        return false;
    }

    @Override
    public boolean subtractFromAccount(double funds, long accountID) {
        return false;
    }

    @Override
    public boolean transaction(double funds, long sender, long receiver) {
        return false;
    }
}

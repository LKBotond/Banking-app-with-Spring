package com.banking.backend.services.transactions.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.backend.dao.accounts.AccountDAO;
import com.banking.backend.dao.masterRecord.MasterRecordDao;
import com.banking.backend.domain.accounts.Account;
import com.banking.backend.services.transactions.TransactionService;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final AccountDAO accountDAO;
    private final MasterRecordDao masterRecordDao;

    public TransactionServiceImpl(AccountDAO accountDAO, MasterRecordDao masterRecordDao) {
        this.accountDAO = accountDAO;
        this.masterRecordDao = masterRecordDao;
    }

    @Override
    public void transaction(long senderID, long receiverID, BigDecimal funds) {
        lockRows(senderID, receiverID);
        updateBalance(senderID, funds.negate());
        updateBalance(receiverID, funds);
        masterRecordDao.recordTransfer(senderID, receiverID, funds);
    }

    @Override
    public void deposit(long accountID, BigDecimal funds) {
        updateBalance(accountID, funds);
    }

    @Override
    public void withdraw(long accountID, BigDecimal funds) {
        updateBalance(accountID, funds.negate());
    }

    private void updateBalance(long accountID, BigDecimal funds) {
        Optional<Account> potentialAccount = accountDAO.getFundsbyAccountID(accountID);
        if (potentialAccount.isEmpty()) {
            throw new RuntimeException("Account not found: " + accountID);
        }
        Account account = potentialAccount.get();
        BigDecimal balance = account.getBalance().add(funds);
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient funds for account: " + accountID);
        }
        accountDAO.updateFundsForAccount(balance, account.getAccountID());
    }

    private void lockRows(long sender, long receiver) {
        List<Account> participants = accountDAO.lockAndGetDataForTransaction(sender, receiver);
        if (participants == null || participants.size() < 2) {
            throw new RuntimeException("A party is missing");
        }
    }

    // lock the rows Tard Also separate it into a specific function
    /**
     * PLAN:
     * have function lockRows(long sender, long receiver)
     * have it return an exception for suspended/deleted
     * have it return an exception for not found
     * have it return two accounts in a list for found and locked
     * 
     * modify transaction function via this, i get the lockable accounts via the
     * lockRows and update the balances,
     * 
     */
}

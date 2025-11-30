package com.banking.backend.services.transactions.impl;

import java.math.BigDecimal;

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
    public Account transaction(long senderID, long receiverID, BigDecimal funds) {
        long first = Math.min(senderID, receiverID);
        long second = Math.max(senderID, receiverID);

        Account acc1 = lockRow(first);
        Account acc2 = lockRow(second);

        Account sender = (acc1.getAccountID() == senderID) ? acc1 : acc2;
        Account receiver = (sender == acc1) ? acc2 : acc1;

        updateBalance(sender, funds.negate());
        updateBalance(receiver, funds);
        masterRecordDao.recordTransfer(senderID, receiverID, funds);
        sender.subtractFromFunds(funds);
        return sender;
    }

    @Override
    public Account deposit(long accountID, BigDecimal funds) {
        Account locked = lockRow(accountID);
        updateBalance(locked, funds);
        masterRecordDao.recordDeposit(accountID, funds);
        locked.addToFunds(funds);
        return locked;
    }

    @Override
    public Account withdraw(long accountID, BigDecimal funds) {
        Account locked = lockRow(accountID);
        updateBalance(locked, funds.negate());
        masterRecordDao.recordWithdrawal(accountID, funds);
        locked.subtractFromFunds(funds);
        return locked;
    }

    private void updateBalance(Account account, BigDecimal funds) {
        BigDecimal balance = account.getBalance().add(funds);
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient funds for account: " + account.getAccountID());
        }
        accountDAO.updateFundsForAccount(balance, account.getAccountID());
    }

    private Account lockRow(long accountId) {
        Account locked = accountDAO.getAccountForTransaction(accountId);
        if (locked == null) {
            throw new RuntimeException("A party is missing");
        }
        return locked;
    }
}

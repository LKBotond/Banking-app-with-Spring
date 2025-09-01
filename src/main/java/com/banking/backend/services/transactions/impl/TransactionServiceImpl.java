package com.banking.backend.services.transactions.impl;

import java.math.BigDecimal;
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
    AccountDAO accountDAO;
    MasterRecordDao masterRecordDao;

    public TransactionServiceImpl(AccountDAO accountDAO, MasterRecordDao masterRecordDao) {
        this.accountDAO = accountDAO;
        this.masterRecordDao = masterRecordDao;
    }

    private void updateBalance(long accountID, BigDecimal funds) {
        Optional<Account> potentialAccount = accountDAO.getAccountByID(accountID);
        if (potentialAccount.isEmpty()) {
            throw new RuntimeException("Account not found: " + accountID);
        }
        Account account = potentialAccount.get();
        BigDecimal balance = account.getBalance().add(funds);
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient funds for account: " + accountID);
        }
        accountDAO.updateFundsForAccount(balance, account.getAccounID());
    }

    @Override
    public void transaction(long senderID, long receiverID, BigDecimal funds) {
        updateBalance(senderID, funds.negate());

        updateBalance(receiverID, funds);
        masterRecordDao.recordTransfer(senderID, receiverID, funds);
    }

    @Override
    public void deposit(long accountID, BigDecimal fubnds) {

    }

    @Override
    public void withdraw(long accountID, BigDecimal fubnds) {
        
    }
}

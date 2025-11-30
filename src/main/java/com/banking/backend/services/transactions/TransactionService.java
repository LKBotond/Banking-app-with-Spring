package com.banking.backend.services.transactions;

import java.math.BigDecimal;

import com.banking.backend.domain.accounts.Account;

public interface TransactionService {

    public Account transaction(long senderID, long receiverID, BigDecimal funds);

    public Account deposit(long accountID, BigDecimal fubnds);

    public Account withdraw(long accountID, BigDecimal fubnds);

}

//NEEED TO CREATE LOGIC FOR CHECKING THE STATUS FLAG FOR ACCOUNTS
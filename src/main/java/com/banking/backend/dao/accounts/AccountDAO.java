package com.banking.backend.dao.accounts;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.banking.backend.domain.accounts.Account;

public interface AccountDAO {

    Account create(long userID);

    Optional<Account> getFundsbyAccountID(long accountID);

    List<Long> getAccountIdsForUser(long userID);

    Optional<List<Account>> getAccountsByUserID(long userID);

    boolean checkForAccountByID(long accountID);

    void updateFundsForAccount(BigDecimal funds, long accountID);

    List<Account> lockAndGetDataForTransaction(long sender, long receiver);

}

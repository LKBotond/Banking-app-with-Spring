package com.banking.backend.dao.accounts;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.banking.backend.domain.accounts.Account;

public interface AccountDAO {

    void create(long userID);

    Optional<Account> getAccountByID(long accountID);

    List<Long> getAccountIdsForUser(long userID);

    List<Account> getAccountsByUserID(long userID);

    boolean checkForAccountByID(long accountID);

    BigDecimal getFundsForAccount(long accountID);

    void updateFundsForAccount(BigDecimal funds, long accountID);

}

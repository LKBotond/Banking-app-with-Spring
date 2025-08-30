package com.banking.backend.dao.accounts;



import com.banking.backend.accounts.Account;

public interface AccountDAO {

    void create(long userID);

    Account getAccountsByUserID(long userID);

    boolean checkForAccountByID(long accountID);

    Double getFundsForAccount(long accountID);

    void updateFundsForAccount(Double funds, long accountID);

}

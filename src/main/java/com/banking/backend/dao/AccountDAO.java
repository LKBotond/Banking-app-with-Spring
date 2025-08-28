package com.banking.backend.dao;

import java.util.ArrayList;

public interface AccountDAO {
    
    void create(long userID);

    ArrayList<Long> getAccountsByUserID(long userID);

    boolean checkForAccountByID(long accountID);
    
    Double getFundsForAccount(long accountID);

    void updateFundsForAccount(Double funds, long accountID);


}

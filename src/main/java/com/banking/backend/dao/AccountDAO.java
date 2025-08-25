package com.banking.backend.dao;

import java.util.ArrayList;

public interface AccountDAO {
    
    void create(Integer userID);

    ArrayList<Integer> getAccountsByUserID(Integer userID);

    boolean checkForAccountByID( Integer accountID);
    
    void updateFundsForAccount(Double funds, Integer accountID);


}

package com.banking.backend.dao;

public interface deletionDao {

    public void deleteUser(long user_id);

    public void deleteUserCrypto(long user_id);

    public void deleteAccount(long accountID);
}

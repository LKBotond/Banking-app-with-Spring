package com.banking.backend.dao.deletion;

public interface DeletionDao {

    public void deleteUser(long user_id);

    public void deleteUserCrypto(long user_id);

    public void deleteAccount(long accountID);
}

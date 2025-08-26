package com.banking.backend.dao;

public interface LoginDao {
    
    public void login(long userID);

    public void logout(long sessionID);

}

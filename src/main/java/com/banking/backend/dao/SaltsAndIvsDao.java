package com.banking.backend.dao;

public interface SaltsAndIvsDao {
    public String getSalt(long userID);

    public String getIV(long userID);

    public void createUserField(long userID, String salt, String iv);

    public void updateSalt(long userID, String salt);

    public void updateIV(long userID, String iv);
}

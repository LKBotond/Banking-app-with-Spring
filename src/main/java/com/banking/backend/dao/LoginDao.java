package com.banking.backend.dao;

import java.util.Optional;

public interface LoginDao {
    
    public Optional<Long> login(long userID);

    public void logout(long sessionID);

}

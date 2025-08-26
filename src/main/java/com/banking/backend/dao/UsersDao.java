package com.banking.backend.dao;

import java.util.Optional;

import com.banking.backend.users.User;

public interface UsersDao {
    void create(User user);

    /**
     * 
     * @param email the registered email
     * @return returns the stored password hash in String format
     */
    Optional<String> checkForUserByEmail(String email);

    Optional<User> getUSerByEmail(String email);

    String getSalt(long userID);

    String getIV(long userID);

}

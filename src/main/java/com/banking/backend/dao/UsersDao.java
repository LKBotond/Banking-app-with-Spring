package com.banking.backend.dao;

import java.util.Optional;

import com.banking.backend.users.User;

public interface UsersDao {

    /**
     * 
     * @param email
     * @param nameEncrypted
     * @param passHash
     */
    void create(String email, String nameEncrypted, String passHash);

    /**
     * 
     * @param email
     * @return the stored password hash in String format 
     * CHECK IF ITS EMPTY
     */
    Optional<String> checkForUserByEmail(String email);

    Optional<User> getUSerByEmail(String email);

    Optional<Long> getUserIDbyEmail(String email);

    String getSalt(long userID);

    String getIV(long userID);

}

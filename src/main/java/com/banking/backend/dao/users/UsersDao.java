package com.banking.backend.dao.users;

import java.util.Optional;

import com.banking.backend.domain.users.User;

public interface UsersDao {

    /**
     * 
     * @param email
     * @param nameEncrypted
     * @param salt
     * @param iv
     * @param passHash
     * @return user_ID
     */
    Optional<Long> create(String email, String nameEncrypted, String salt, String iv, String passHash);

    /**
     * 
     * @param email
     * @return the stored password hash in String format
     *         CHECK IF ITS EMPTY
     */
    Optional<String> checkForUserByEmail(String email);

    /**
     * 
     * @param email
     * @return Base user object for authentication
     * 
     */
    Optional<User> getUserByEmail(String email);

    /**
     * 
     * @param email
     * @return only the User's id
     */
    Optional<Long> getUserIDbyEmail(String email);

    /**
     * 
     * @param userID
     * @return returns the password hash
     */
    Optional<String> getPassHashByID(long userID);

    public Optional<String> getSalt(long userID);

    public Optional<String> getIV(long userID);

    public void updateSalt(long userID, String salt);

    public void updateIV(long userID, String iv);

}

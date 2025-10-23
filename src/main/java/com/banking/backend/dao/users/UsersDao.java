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
    Long create(String email, String nameEncrypted, String salt, String iv, String passHash);

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
    String getPassHashByID(long userID);

    public String getSalt(long userID);

    public String getIV(long userID);

    public void updateSalt(long userID, String salt);

    public void updateIV(long userID, String iv);

}

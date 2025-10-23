package com.banking.backend.domain.users;

import java.util.ArrayList;

import com.banking.backend.Legacy.domain.accounts.Account;

//redesign a bit, tldr, do not need the whole thingy, just the specifics, meaning separate register and login dao and stuff

public class User {
    private long userID;
    private String email;
    private String encryptedName;
    private String IV;
    private String name;
    private String familyName;
    private char[] passHash;
    private ArrayList<Account> accounts;

    /**
     * Creates a user domain object
     * <p>
     * Note: The {@code accounts} field remains uninitialized and must be set
     * afterward.
     */
    public User(int userID, String email, String encryptedName, String IV) {
        this.userID = userID;
        this.email = email;
        this.encryptedName = encryptedName;
        this.IV = IV;
    }

    /**
     * Creates an empty user domain object
     */
    public static User empty() {
        return new User(0, null, null, null);
    }

    /**
     * Sets the {@code accounts} field for the User domain object
     */
    public void setAccountsForUser(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Adds an {@code account} to the {@code accounts} field for the User domain
     * object
     */
    public void addNewAccountToUser(Account account) {
        this.accounts.add(account);
    }

    /**
     * Returns an {@code Account} if the specified ID exists in the list; otherwise,
     * returns {@code null}.
     *
     * @param id the ID of the account to find
     * @return the matching {@code Account}, or {@code null} if not found
     */

    public Account getAccountByID(int accountID) {
        for (Account account : accounts) {
            if (account.getAccounID() == accountID) {
                return account;
            }
        }
        return null;
    }

    /**
     * Returns all accounts associated with the user.
     *
     * @return an {@code ArrayList} of {@code Account} objects
     */
    public ArrayList<Account> getAllAccounts() {
        return accounts;
    }

    public void splitName(String fullName) {
        String[] parts = fullName.split(" ", 2);
        this.name = parts[0];
        if (parts.length > 1) {
            this.familyName = parts[1];
        } else {
            this.familyName = "";
        }

    }

    public String getEncryptedName() {
        return this.encryptedName;
    }

    public String getIV() {
        return this.IV;
    }

    public String getName() {
        return this.name;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public String getEmail() {
        return this.email;
    }

    public long getUserId() {
        return this.userID;
    }

    public char[] getPassHash() {
        return this.passHash;
    }

}

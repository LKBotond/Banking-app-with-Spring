package com.banking.backend.domain.users;

import java.util.ArrayList;

import com.banking.backend.domain.accounts.Account;
import lombok.Data;

@Data
public class User {
    // Identifications
    private long userID;
    private String email;

    // Security
    private String passHash;
    private String IV;
    private String salt;

    // Name
    private String encryptedName;
    private String name;
    private String familyName;
    private ArrayList<Account> accounts = new ArrayList<>();

    /**
     * Creates a user domain object
     * <p>
     * Note: The {@code accounts} field remains uninitialized and must be set
     * afterward.
     */
    public User(long userID, String email, String encryptedName, String salt, String IV, String passHash) {
        this.userID = userID;
        this.email = email;
        this.encryptedName = encryptedName;
        this.salt = salt;
        this.IV = IV;
        this.passHash = passHash;
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
            if (account.getAccountID() == accountID) {
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

}

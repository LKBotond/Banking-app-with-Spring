package com.banking.backend.users;

import java.util.ArrayList;

import com.banking.backend.accounts.Account;

//redesign a bit, tldr, do not need the whole thingy, just the specifics, meaning separate register and login dao and stuff

public class User {
    private long userID;
    private long sessionID;
    private String email;
    private String encryptedName;
    private String IV;
    private String name;
    private String familyName;
    private char[] passHash;
    private ArrayList<Account> accounts;

    public User(int user_ID, long sessionID, String email, String encryptedName, String IV) {
        this.userID = user_ID;
        this.sessionID = sessionID;
        this.email = email;
        this.encryptedName = encryptedName;
        this.IV = IV;
    }

    public static User empty() {
        return new User(0, 0, null, null, null);
    }

    public void setAccountsForUser(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public void addNewAccountToUser(Account account) {
        this.accounts.add(account);
    }

    public Account getAccountByID(int accountID) {
        for (Account account : accounts) {
            if (account.getAccounID() == accountID) {
                return account;
            }
        }
        return null;
    }

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

    public long getSessionID(){
        return this.sessionID;
    }

    public char[] getPassHash() {
        return this.passHash;
    }

}

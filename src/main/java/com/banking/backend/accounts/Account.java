package com.banking.backend.accounts;

public class Account {
    private int accountID;
    private int funds;

    public Account(int accountID, int funds) {
        this.accountID = accountID;
        this.funds = funds;
    }

    public int getBalance() {
        return funds;
    }

    public int getAccounID() {
        return accountID;
    }

    public void addToFunds(int amounth) {
        funds += amounth;
    }

    public boolean subtractFromFunds(int amount) {
        if (funds >= amount) {
            funds -= amount;
            return true;
        }
        return false;
    }
}
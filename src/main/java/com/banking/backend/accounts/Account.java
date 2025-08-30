package com.banking.backend.accounts;

import java.math.BigDecimal;

public class Account {
    private long accountID;
    private BigDecimal funds;

    public Account(long accountID, BigDecimal funds) {
        this.accountID = accountID;
        this.funds = funds;
    }

    public BigDecimal getBalance() {
        return funds;
    }

    public long getAccounID() {
        return accountID;
    }

    public void addToFunds(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        funds = funds.add(amount);
    }

    public boolean subtractFromFunds(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (funds.compareTo(amount) > 0) {
            funds = funds.subtract(amount);
            return true;
        }
        return false;
    }
}
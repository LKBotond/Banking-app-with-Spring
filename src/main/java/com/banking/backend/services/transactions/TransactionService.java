package com.banking.backend.services.transactions;

public interface TransactionService {

    public boolean addToAccount(double funds, long accountID);

    public boolean subtractFromAccount(double funds, long accountID);

    public boolean transaction(double funds, long sender, long receiver);

}

package com.banking.backend.dao.accounts;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.banking.backend.dao.BaseDaoImpl;
import com.banking.backend.dbAccess.DBQueries;
import com.banking.backend.domain.accounts.Account;
import com.banking.backend.exceptions.DataBaseAccessException;

@Repository
@Transactional
public class AccountDaoImpl extends BaseDaoImpl implements AccountDAO {

    public AccountDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(long userID) {
        updateDB(DBQueries.CREATE_ACCOUNT, userID);
    }

    @Override
    public Optional<Account> getFundsbyAccountID(long accountID) {
        Optional<BigDecimal> potential = getScalar(DBQueries.GET_FUNDS_FOR_ACCOUNT, BigDecimal.class, accountID);
        if (potential.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Account(accountID, potential.get()));
    }

    @Override
    public List<Long> getAccountIdsForUser(long userID) {
        return getSingleColumnList(DBQueries.GET_ACCOUNT_IDS, Long.class, userID);
    }

    @Override
    public Optional<List<Account>> getAccountsByUserID(long userID) {
        return getResultList(DBQueries.GET_ALL_ACCOUNTS_FOR_USER, accountRowMapper(), userID);
    }

    @Override
    public boolean checkForAccountByID(long accountID) {
        return getScalar(DBQueries.GET_ACCOUNT_BY_ID, Long.class, accountID).isPresent();
    }

    @Override
    public void updateFundsForAccount(BigDecimal funds, long accountID) {
        updateDB(DBQueries.UPDATE_FUNDS_FOR_ACCOUNT_ID, funds, accountID);
    }

    @Override
    public List<Account> lockAndGetDataForTransaction(long sender, long receiver) {
        Optional<List<Account>> potentialAccounts = getResultList(DBQueries.LOCK_FOR_TRANSACTION, accountRowMapper(),
                sender, receiver);
        if (potentialAccounts.isEmpty()) {
            throw new DataBaseAccessException("Missing data for transaction between ids: " + sender + " " + receiver,
                    null);
        }
        List<Account> accounts = potentialAccounts.get();
        if (accounts.size() != 2) {
            throw new DataBaseAccessException("Too many or too few accounts for the query", null);
        }
        return accounts;

    }

    private RowMapper<Account> accountRowMapper() {
        return (rs, _) -> new Account(
                rs.getLong("id"),
                rs.getBigDecimal("fund"));
    }
}

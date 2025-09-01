package com.banking.backend.dao.accounts;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.banking.backend.accounts.Account;
import com.banking.backend.dao.BaseDaoImpl;
import com.banking.backend.dbAccess.DBQueries;

@Repository
@Transactional
public class AccountDaoImpl extends BaseDaoImpl implements AccountDAO {

    public AccountDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private RowMapper<Account> accountRowMapper() {
        return (rs, _) -> new Account(
                rs.getLong("account_id"),
                rs.getBigDecimal("fund"));
    }

    @Override
    public void create(long userID) {
        addData(DBQueries.CREATE_ACCOUNT, userID);
    }

    @Override
    public Optional<Account> getAccountByID(long accountID) {
        Optional<BigDecimal> potential = getScalar(DBQueries.GET_FUNDS_FOR_ACCOUNT, BigDecimal.class, accountID);
        if (potential.isEmpty()) {
            Optional.empty();
        }
        return Optional.of(new Account(accountID, potential.get()));
    }

    @Override
    public List<Long> getAccountIdsForUser(long userID) {
        return getSingleColumnList(DBQueries.GET_ACCOUNT_IDS, Long.class, userID);
    }

    // return account without the row mapper, user it within
    @Override
    public List<Account> getAccountsByUserID(long userID) {
        return getResultList(DBQueries.GET_ALL_ACCOUNTS_FOR_USER, accountRowMapper(), userID);
    }

    @Override
    public boolean checkForAccountByID(long accountID) {
        try {
            Long result = jdbcTemplate.queryForObject(DBQueries.GET_ACCOUNT_BY_ID, Long.class, accountID);
            return result != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public BigDecimal getFundsForAccount(long accountID) {
        return jdbcTemplate.queryForObject(DBQueries.GET_FUNDS_FOR_ACCOUNT, BigDecimal.class, accountID);
    }

    @Override
    public void updateFundsForAccount(Double funds, long accountID) {
        jdbcTemplate.update(DBQueries.UPDATE_FUNDS_FOR_ACCOUNT_ID, funds, accountID);
    }
}

package com.banking.backend.dao.impl;

import java.util.ArrayList;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.banking.backend.dao.AccountDAO;
import com.banking.backend.dbAccess.DBQueries;

@Repository
public class AccountDaoImpl implements AccountDAO {
    private final JdbcTemplate jdbcTemplate;

    public AccountDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(long userID) {
        jdbcTemplate.update(DBQueries.CREATE_ACCOUNT, userID);
    }

    @Override
    public ArrayList<Long> getAccountsByUserID(long userID) {
        return new ArrayList<Long>(jdbcTemplate.queryForList(DBQueries.GET_ACCOUNT_IDS, Long.class, userID));
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
    public Double getFundsForAccount(long accountID) {
        return jdbcTemplate.queryForObject(DBQueries.GET_FUNDS_FOR_ACCOUNT, Double.class, accountID);
    }

    @Override
    public void updateFundsForAccount(Double funds, long accountID) {
        jdbcTemplate.update(DBQueries.UPDATE_FUNDS_FOR_ACCOUNT_ID, funds, accountID);
    }
}

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
    public void create(Integer userID) {
        jdbcTemplate.update(DBQueries.CREATE_ACCOUNT, userID);
    }

    @Override
    public ArrayList<Integer> getAccountsByUserID(Integer userID) {
        return new ArrayList<Integer>(jdbcTemplate.queryForList(DBQueries.GET_ACCOUNT_IDS, Integer.class, userID));
    }

    @Override
    public boolean checkForAccountByID(Integer accountID) {
        try {
            Integer result = jdbcTemplate.queryForObject(DBQueries.GET_ACCOUNT_BY_ID, Integer.class, accountID);
            return result != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public void updateFundsForAccount(Double funds, Integer accountID) {
        jdbcTemplate.update(DBQueries.UPDATE_FUNDS_FOR_ACCOUNT_ID, funds, accountID);
    }
}

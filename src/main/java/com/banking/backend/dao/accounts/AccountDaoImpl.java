package com.banking.backend.dao.accounts;

import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.banking.backend.accounts.Account;
import com.banking.backend.dao.BaseDaoImpl;
import com.banking.backend.dbAccess.DBQueries;

@Repository
public class AccountDaoImpl extends BaseDaoImpl implements AccountDAO {

    public AccountDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(long userID) {
        addData(DBQueries.CREATE_ACCOUNT, userID);
    }

    //return account without the row mapper, user it within
    @Override
    @Transactional
    public Account getAccountsByUserID(long userID) {
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

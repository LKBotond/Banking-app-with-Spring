package com.banking.backend.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.banking.backend.dao.deletionDao;
import com.banking.backend.dbAccess.DBQueries;

public class deletionDaoImpl implements deletionDao {
    private final JdbcTemplate jdbcTemplate;

    public deletionDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void deleteUser(long userID) {
        jdbcTemplate.update(DBQueries.DELETE_USER, userID);
    }

    public void deleteUserCrypto(long userID) {
        jdbcTemplate.update(DBQueries.DELETE_USER, userID);
    }

    public void deleteAccount(long accountID) {
        jdbcTemplate.update(DBQueries.DELETE_USER, accountID);
    }
}

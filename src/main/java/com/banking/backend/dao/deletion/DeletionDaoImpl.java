package com.banking.backend.dao.deletion;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.banking.backend.dbAccess.DBQueries;

@Repository
public class DeletionDaoImpl implements DeletionDao {
    private final JdbcTemplate jdbcTemplate;

    public DeletionDaoImpl(JdbcTemplate jdbcTemplate) {
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

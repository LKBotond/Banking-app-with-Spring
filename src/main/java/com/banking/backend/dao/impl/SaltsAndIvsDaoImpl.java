package com.banking.backend.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.banking.backend.dao.SaltsAndIvsDao;
import com.banking.backend.dbAccess.DBQueries;

@Repository
public class SaltsAndIvsDaoImpl implements SaltsAndIvsDao {

    private final JdbcTemplate jdbcTemplate;

    public SaltsAndIvsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getSalt(long userID) {
        return jdbcTemplate.queryForObject(DBQueries.GET_SALT, String.class, userID);
    }

    @Override
    public String getIV(long userID) {
        return jdbcTemplate.queryForObject(DBQueries.GET_IV, String.class, userID);
    }

    @Override
    public void createUserField(long userID, String salt, String iv) {
        jdbcTemplate.update(DBQueries.CREATE_USER_FIELD, userID, salt, iv);
    }

    @Override
    public void updateSalt(long userID, String salt) {
        jdbcTemplate.update(DBQueries.UDATE_SALT, userID, salt);
    }

    @Override
    public void updateIV(long userID, String iv) {
        jdbcTemplate.update(DBQueries.UPDATE_IV, userID, iv);
    }
}

package com.banking.backend.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.banking.backend.dao.LoginDao;
import com.banking.backend.dbAccess.DBQueries;

@Repository
public class LoginDaoImpl implements LoginDao {

    private final JdbcTemplate jddJdbcTemplate;

    public LoginDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jddJdbcTemplate = jdbcTemplate;
    }

    @Override
    public void login(long userID) {
        jddJdbcTemplate.update(DBQueries.LOGIN, userID);
    }

    @Override
    public void logout(long sessionID) {
        jddJdbcTemplate.update(DBQueries.LOGOUT, sessionID);
    }

}

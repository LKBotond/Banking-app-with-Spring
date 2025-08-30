package com.banking.backend.dao.logins;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.banking.backend.dbAccess.DBQueries;

@Repository
public class LoginDaoImpl implements LoginDao {

    private final JdbcTemplate jddJdbcTemplate;

    public LoginDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jddJdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Long> login(long userID) {
        try {
            Long rs = jddJdbcTemplate.queryForObject(DBQueries.LOGIN, Long.class, userID);
            return Optional.of(rs);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void logout(long sessionID) {
        jddJdbcTemplate.update(DBQueries.LOGOUT, sessionID);
    }

}

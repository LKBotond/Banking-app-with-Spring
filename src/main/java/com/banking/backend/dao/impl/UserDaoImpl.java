package com.banking.backend.dao.impl;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.banking.backend.dao.UsersDao;
import com.banking.backend.dbAccess.DBQueries;
import com.banking.backend.users.User;

@Repository
public class UserDaoImpl implements UsersDao {

    JdbcTemplate jdbcTemplate;

    UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User user) {

        jdbcTemplate.update(DBQueries.CREATE_USER,
                user.getEmail(),
                user.getEncryptedName(),
                user.getIV(),
                user.getPassHash());

    }

    @Override
    public Optional<String> checkForUserByEmail(String email) {
        try {
            String passHash = jdbcTemplate.queryForObject(DBQueries.GET_PASS_HASH, String.class, email);
            return Optional.ofNullable(passHash);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUSerByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject(DBQueries.GET_USER, (rs, _) -> new User(
                    rs.getInt("user_id"),
                    rs.getInt("session_id"),
                    email,
                    rs.getString("name_encrypted"),
                    rs.getString("iv")),
                    email);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public String getSalt(long userID) {
        return jdbcTemplate.queryForObject(DBQueries.GET_SALT, String.class, userID);
    }

    public String getIV(long userID) {
        return jdbcTemplate.queryForObject(DBQueries.GET_IV, String.class, userID);
    }

}

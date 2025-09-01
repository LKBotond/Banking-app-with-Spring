package com.banking.backend.dao.users;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.banking.backend.dbAccess.DBQueries;
import com.banking.backend.domain.users.User;

@Repository
public class UserDaoImpl implements UsersDao {

    JdbcTemplate jdbcTemplate;

    UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long create(String email, String nameEncrypted, String passHash) {
        try {
            return jdbcTemplate.queryForObject(DBQueries.CREATE_USER,
                    Long.class,
                    email,
                    nameEncrypted,
                    passHash);
        } catch (DataAccessException e) {
            return null;
        }
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

    public Optional<Long> getUserIDbyEmail(String email) {
        try {
            Long userID = jdbcTemplate.queryForObject(DBQueries.GET_USER_ID, Long.class, email);
            return Optional.ofNullable(userID);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public String getPassHashByID(long userID) {
        return jdbcTemplate.queryForObject(DBQueries.GET_PASS_HASH_BY_ID, String.class, userID);
    }

    @Override
    public String getSalt(long userID) {
        return jdbcTemplate.queryForObject(DBQueries.GET_SALT, String.class, userID);
    }

    @Override
    public String getIV(long userID) {
        return jdbcTemplate.queryForObject(DBQueries.GET_IV, String.class, userID);
    }

}

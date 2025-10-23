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
    public Long create(String email, String nameEncrypted, String salt, String iv, String passHash) {
        try {
            return jdbcTemplate.queryForObject(DBQueries.CREATE_USER,
                    Long.class,
                    email,
                    nameEncrypted,
                    salt,
                    iv,
                    passHash);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Optional<String> checkForUserByEmail(String email) {
        try {
            String passHash = jdbcTemplate.queryForObject(DBQueries.GET_PASS_HASH_BY_EMAIL, String.class, email);
            return Optional.ofNullable(passHash);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject(DBQueries.GET_USER, (rs, _) -> new User(
                    rs.getInt("user_id"),
                    email,
                    rs.getString("name_encrypted"),
                    rs.getString("salt"),
                    rs.getString("iv"),
                    rs.getString("pass_hash")),
                    email);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public Optional<Long> getUserIDbyEmail(String email) {
        try {
            Long userID = jdbcTemplate.queryForObject(DBQueries.GET_USER_ID_BY_EMAIL, Long.class, email);
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

    @Override
    public void updateSalt(long userID, String salt) {
        jdbcTemplate.update(DBQueries.UDATE_SALT, userID, salt);
    }

    @Override
    public void updateIV(long userID, String iv) {
        jdbcTemplate.update(DBQueries.UPDATE_IV, userID, iv);
    }
}

package com.banking.backend.dao.users;

import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.banking.backend.dao.BaseDaoImpl;
import com.banking.backend.dbAccess.DBQueries;
import com.banking.backend.domain.users.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl implements UsersDao {

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<Long> create(String email, String nameEncrypted, String salt, String iv, String passHash) {
        return getScalar(DBQueries.CREATE_USER, Long.class, email,
                nameEncrypted,
                salt,
                iv,
                passHash);
    }

    @Override
    public Optional<String> checkForUserByEmail(String email) {
        return getScalar(DBQueries.GET_PASS_HASH_BY_EMAIL, String.class, email);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return getSingleRow(DBQueries.GET_USER, partialUserMapper(email), email);
    }

    public Optional<Long> getUserIDbyEmail(String email) {
        return getScalar(DBQueries.GET_USER_ID_BY_EMAIL, Long.class, email);
    }

    @Override
    public Optional<String> getPassHashByID(long userID) {
        return getScalar(DBQueries.GET_PASS_HASH_BY_ID, String.class, userID);
    }

    @Override
    public Optional<String> getSalt(long userID) {
        return getScalar(DBQueries.GET_SALT, String.class, userID);
    }

    @Override
    public Optional<String> getIV(long userID) {
        return getScalar(DBQueries.GET_IV, String.class, userID);
    }

    @Override
    public void updateSalt(long userID, String salt) {
        updateDB(DBQueries.UDATE_SALT, userID, salt);
    }

    @Override
    public void updateIV(long userID, String iv) {
        updateDB(DBQueries.UPDATE_IV, userID, iv);
    }

    private RowMapper<User> partialUserMapper(String email) {

        return (rs, _) -> new User(
                rs.getLong("user_id"),
                email,
                rs.getString("name_encrypted"),
                rs.getString("salt"),
                rs.getString("iv"),
                rs.getString("pass_hash"));
    }
}

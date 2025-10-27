package com.banking.backend.dao.logins;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.banking.backend.dbAccess.DBQueries;
import com.banking.backend.dao.BaseDaoImpl;

@Repository
public class LoginDaoImpl extends BaseDaoImpl implements LoginDao {

    public LoginDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<Long> login(long userID) {
        return getScalar(DBQueries.LOGIN, Long.class, userID);
    }

    @Override
    public void logout(long loginId) {
        updateDB(DBQueries.LOGOUT, loginId);
    }

}

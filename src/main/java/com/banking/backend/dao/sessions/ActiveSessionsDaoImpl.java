package com.banking.backend.dao.sessions;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.banking.backend.dao.BaseDaoImpl;
import com.banking.backend.dbAccess.DBQueries;

@Repository
public class ActiveSessionsDaoImpl extends BaseDaoImpl implements ActiveSessionsDao {

    public ActiveSessionsDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public Optional<Long> getNumberOfActiveUsers() {
        return getScalar(DBQueries.GET_NUMBER_OF_CURRENTLY_ACTIVE_USERS, Long.class);
    }

    public Optional<Long> getUsersLoginId(String sessionId) {
        return getScalar(DBQueries.GET_LOGIN_ID, Long.class, sessionId);
    }

    public Optional<Long> getUserIdbySessionId(String sessionToken) {
        return getScalar(DBQueries.GET_USER_ID_BY_SESSION_ID, Long.class, sessionToken);
    }

    public void addActiveSession(String sessionId, long loginId) {
        updateDB(DBQueries.ADD_SESSION, loginId, sessionId);
    }

    public void deleteActiveSession(String sessionId) {
        updateDB(DBQueries.DELETE_SESSION, sessionId);
    }
}

package com.banking.backend.dao.sessions;

import java.util.Optional;

public interface ActiveSessionsDao {

    Optional<Long> getNumberOfActiveUsers();

    Optional<Long> getUsersLoginId(String sessionId);

    Optional<Long> getUserIdbySessionId(String sessionToken);

    void addActiveSession(String sessionId, long loginId);

    void deleteActiveSession(String sessionId);
}

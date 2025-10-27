package com.banking.backend.services.session;

import org.springframework.stereotype.Service;

import com.banking.backend.dao.sessions.ActiveSessionsDao;
import com.banking.backend.dto.access.AccessToken;
import com.banking.backend.services.security.Argon2KDF;

import lombok.AllArgsConstructor;
import java.util.Base64;

@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final ActiveSessionsDao sessionDao;
    private final Argon2KDF argon2KDF;
    private static final int TOKEN_BYTES = 32;
    private final SessionCache sessionCache;

    public boolean validateSession(String sessionToken) {
        if (sessionToken == null || sessionToken.isBlank()) {
            return false;
        }
        if (sessionCache.getSession(sessionToken) != null) {
            return true;
        }
        Long loginId = lookupInDataBase(sessionToken);
        if (loginId != null) {
            updateCache(sessionToken, loginId);
            return true;
        }
        return false;
    }

    public void createSessionToken(AccessToken incompleteAccessToken, long loginId) {
        final String sessionToken = createSessionString();
        this.sessionDao.addActiveSession(sessionToken, loginId);
        this.sessionCache.addSession(sessionToken, loginId);
        incompleteAccessToken.setSessionToken(sessionToken);
    }

    public void endSession(String sessionToken) {
        sessionCache.removeSession(sessionToken);
        this.sessionDao.deleteActiveSession(sessionToken);
    }

    private String createSessionString() {
        return Base64.getEncoder().encodeToString(this.argon2KDF.getRandom(TOKEN_BYTES));
    }

    private Long lookupInDataBase(String sessionToken) {
        return this.sessionDao.getUsersLoginId(sessionToken).orElse(null);
    }
    private void updateCache(String sessionToken, Long loginId) {
        this.sessionCache.addSession(sessionToken, loginId);
    }
}

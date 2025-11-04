package com.banking.backend.services.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.banking.backend.dao.sessions.ActiveSessionsDao;
import com.banking.backend.dto.access.AccessToken;
import com.banking.backend.exceptions.InvalidSessionException;
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
    private static final Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);

    public boolean validateSession(String sessionToken) {
        log.info("Session validation reached");
        if (sessionToken == null || sessionToken.isBlank()) {
            log.info("invalid session");
            return false;
        }
        if (sessionCache.getSession(sessionToken) != null) {
            log.info("valid session");
            return true;
        }
        log.info("Querying db for session");
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

    public Long getUserIdBySession(String sessionToken) {
        return sessionDao.getUserIdbySessionId(sessionToken).orElseThrow(InvalidSessionException::new);
    }

    private String createSessionString() {
        return Base64.getUrlEncoder().encodeToString(this.argon2KDF.getRandom(TOKEN_BYTES));
    }

    private Long lookupInDataBase(String sessionToken) {
        return this.sessionDao.getUsersLoginId(sessionToken).orElseThrow(InvalidSessionException::new);
    }

    private void updateCache(String sessionToken, Long loginId) {
        this.sessionCache.addSession(sessionToken, loginId);
    }
}

package com.banking.backend.services.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SessionCache {
    private final Map<String, Long> activeSessions = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void addSession(String sessionId, Long loginId) {
        activeSessions.put(sessionId, loginId);
    }

    public Long getSession(String sessionId) {
        return activeSessions.get(sessionId);
    }

    public void removeSession(String sessionId) {
        activeSessions.remove(sessionId);
    }

    public void logAllSessionsPretty() {
        try {
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(activeSessions);
            log.info("All active sessions:\n{}", json);
        } catch (Exception e) {
            log.error("Error logging sessions", e);
        }

    }
}

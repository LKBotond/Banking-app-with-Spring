package com.banking.backend.services.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class SessionCache {
    private final Map<String, Long> activeSessions = new ConcurrentHashMap<>();
    public void addSession(String sessionId, Long loginId) {
        activeSessions.put(sessionId, loginId);
    }

    public Long getSession(String sessionId) {
        return activeSessions.get(sessionId);
    }

    public void removeSession(String sessionId) {
        activeSessions.remove(sessionId);
    }

}

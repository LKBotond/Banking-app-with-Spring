package com.banking.backend.services.access;

//Spring Specific
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//DTOs
import com.banking.backend.dto.authentication.LogoutRequestDTO;

//DAOs
import com.banking.backend.dao.logins.LoginDao;
import com.banking.backend.dao.sessions.ActiveSessionsDao;

//Utils
import java.util.Optional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LogoutService {
ActiveSessionsDao activeSessionsDao;
LoginDao loginDao;
    @Transactional
    public Optional<Object> logout(LogoutRequestDTO logoutRequest) {
        long loginId = activeSessionsDao.getUsersLoginId(logoutRequest.getSessionId()).get();
        this.loginDao.logout(loginId);
        this.activeSessionsDao.deleteActiveSession(logoutRequest.getSessionId());
        return Optional.of(200);
    }
}

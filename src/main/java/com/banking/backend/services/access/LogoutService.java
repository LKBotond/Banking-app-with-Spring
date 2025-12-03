package com.banking.backend.services.access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//Spring Specific
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//DTOs
import com.banking.backend.dto.authentication.LogoutRequestDTO;
import com.banking.backend.exceptions.LoginIdNotFoundException;
//DAOs
import com.banking.backend.dao.logins.LoginDao;
import com.banking.backend.dao.sessions.ActiveSessionsDao;

//Utils
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LogoutService {
    ActiveSessionsDao activeSessionsDao;
    LoginDao loginDao;
    private static final Logger log = LoggerFactory.getLogger(LogoutService.class);

    @Transactional
    public void logout(LogoutRequestDTO request) {
        log.info("Logout Service called");
        long loginId = activeSessionsDao.getUsersLoginId(request.getSessionId())
                .orElseThrow(LoginIdNotFoundException::new);
        log.info("Got LoginI:{}", loginId);
        this.loginDao.logout(loginId);
        log.info("Closed login table:{}", loginId);
        this.activeSessionsDao.deleteActiveSession(request.getSessionId());
        log.info("deleted SessionId:{}", loginId);
    }
}

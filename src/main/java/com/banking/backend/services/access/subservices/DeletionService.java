package com.banking.backend.services.access.subservices;

//SPring Specific
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//DAOs
import com.banking.backend.dao.accounts.AccountDAO;
import com.banking.backend.dao.deletion.DeletionDao;
import com.banking.backend.dao.sessions.ActiveSessionsDao;
import com.banking.backend.dao.users.UsersDao;

//Domains
import com.banking.backend.domain.accounts.Account;

//DTOs
import com.banking.backend.dto.authentication.DeletionRequestDTO;

//Services
import com.banking.backend.services.security.AuthenticationService;

import lombok.AllArgsConstructor;
import java.util.Optional;
import java.util.List;

@Service
@AllArgsConstructor
public class DeletionService {
    AuthenticationService authenticationService;
    
    UsersDao userDao;
    AccountDAO accountDao;
    DeletionDao deletionDao;
    ActiveSessionsDao activeSessionsDao;

    @Transactional
    public Optional<Object> deleteUser(DeletionRequestDTO deletionRequest) {
        long userId = this.activeSessionsDao.getUserIdbySessionId(deletionRequest.getSessionId()).get();
        String passOnRecord = this.userDao.getPassHashByID(userId);

        if (!authenticationService.verifyPass(passOnRecord, deletionRequest.getPassword())) {
            return Optional.of(401);
        }
        List<Account> accounts = this.accountDao.getAccountsByUserID(userId);
        for (Account account : accounts) {
            deletionDao.deleteAccount(account.getAccountID());
        }

        deletionDao.deleteUser(userId);
        return Optional.of(200);
    }

}

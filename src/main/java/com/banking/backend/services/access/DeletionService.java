package com.banking.backend.services.access;

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
import com.banking.backend.exceptions.InvalidSessionException;
import com.banking.backend.exceptions.UserNotFoundException;
import com.banking.backend.exceptions.WrongPasswordException;
//Services
import com.banking.backend.services.security.AuthenticationService;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
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
    public void deleteUser(DeletionRequestDTO request) {
        Long userId = activeSessionsDao.getUserIdbySessionId(request.getSessionId())
                .orElseThrow(InvalidSessionException::new);
        String passOnRecord = userDao.getPassHashByID(userId).orElseThrow(UserNotFoundException::new);
        if (!authenticationService.verifyPass(passOnRecord, request.getPassword())) {
            throw new WrongPasswordException();
        }
        List<Account> accounts = getAccounts(userId);
        for (Account account : accounts) {
            deletionDao.deleteAccount(account.getAccountID());
        }

        deletionDao.deleteUser(userId);
        activeSessionsDao.deleteActiveSession(request.getSessionId());

    }

    private ArrayList<Account> getAccounts(long userId) {
        return new ArrayList<Account>(this.accountDao.getAccountsByUserID(userId).orElse(new ArrayList<Account>()));
    }
}

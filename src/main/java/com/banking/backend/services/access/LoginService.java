package com.banking.backend.services.access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//Spring Specific
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Services
import com.banking.backend.services.security.Argon2KDF;
import com.banking.backend.services.security.AuthenticationService;
import com.banking.backend.services.security.Encryptor;
import com.banking.backend.services.session.SessionService;
//DTOs
import com.banking.backend.dto.access.AccessToken;
import com.banking.backend.dto.authentication.LoginRequestDTO;
import com.banking.backend.exceptions.LoginIdNotFoundException;
import com.banking.backend.exceptions.UserNotFoundException;
import com.banking.backend.exceptions.WrongPasswordException;
//DAOs
import com.banking.backend.dao.accounts.AccountDAO;
import com.banking.backend.dao.logins.LoginDao;
import com.banking.backend.dao.users.UsersDao;

//Domains
import com.banking.backend.domain.accounts.Account;
import com.banking.backend.domain.users.User;

//Utils
import java.util.Base64;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import javax.crypto.SecretKey;

@Service
@AllArgsConstructor
public class LoginService {
    AuthenticationService authenticationService;
    SessionService sessionService;
    Encryptor encryptor;
    Argon2KDF argon2KDF;

    UsersDao userDao;
    LoginDao loginDao;
    AccountDAO accountDao;

    private static final Logger log = LoggerFactory.getLogger(LoginService.class);

    // Main
    @Transactional
    public AccessToken login(LoginRequestDTO request) {
        log.info("Login service called");
        User user = authenticateUser(request);
        try {
            setAccountsForUser(user);
            user.splitName(decryptUserName(user, request.getPassword()));
            AccessToken accessToken = createIncompleteAccessToken(user);
            Long loginId = this.loginDao.login(user.getUserID()).orElseThrow(LoginIdNotFoundException::new);
            sessionService.createSessionToken(accessToken, loginId);
            return accessToken;
        } finally {
            authenticationService.wipeSensitiveMemory(request.getPassword());
        }

    }

    // Helpers
    private User authenticateUser(LoginRequestDTO request) {
        User user = userDao.getUserByEmail(request.getEmail()).orElseThrow(UserNotFoundException::new);
        if (!authenticationService.verifyPass(user.getPassHash(), request.getPassword())) {
            throw new WrongPasswordException();
        }
        return user;
    }

    private ArrayList<Account> getAccounts(long userId) {
        return new ArrayList<Account>(this.accountDao.getAccountsByUserID(userId).orElse(new ArrayList<Account>()));
    }

    private void setAccountsForUser(User user) {
        long id = user.getUserID();
        ArrayList<Account> accounts = getAccounts(id);
        user.setAccounts(accounts);
    }

    private String decryptUserName(User user, char[] password) {
        final String salt = user.getSalt();
        final String iv = user.getIV();
        final SecretKey decryptionKey = this.argon2KDF.deriveKey(password, Base64.getDecoder().decode(salt));
        final String decryptedName = this.encryptor.decryptWithAESGCM(user.getEncryptedName(),
                Base64.getDecoder().decode(iv), decryptionKey);
        return decryptedName;
    }

    private AccessToken createIncompleteAccessToken(User user) {
        return new AccessToken(null, user.getName(), user.getFamilyName());
    }
}
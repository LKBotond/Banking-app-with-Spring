package com.banking.backend.services.access;

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

    // Main
    @Transactional
    public AccessToken login(LoginRequestDTO loginRequest) {
        User user = this.userDao.getUserByEmail(loginRequest.getEmail()).orElseThrow(UserNotFoundException::new);
        if (!authenticationService.verifyPass(user.getPassHash(), loginRequest.getPassword())) {
            throw new WrongPasswordException();
        }
        setAccountsForUser(user);
        user.splitName(decryptUserName(user, loginRequest.getPassword()));
        AccessToken accessToken = createIncompleteAccessToken(user);
        final long loginId = this.loginDao.login(user.getUserID()).get();
        sessionService.createSessionToken(accessToken, loginId);
        authenticationService.wipePass(loginRequest.getPassword());
        return accessToken;
    }

    // Helpers

    private ArrayList<Account> getAccounts(long userId) {
        return new ArrayList<>(this.accountDao.getAccountsByUserID(userId));
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
        return new AccessToken(null, user.getName(), user.getFamilyName(),
                user.getAllAccounts());
    }
}

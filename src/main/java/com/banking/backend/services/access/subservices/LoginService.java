package com.banking.backend.services.access.subservices;

//Spring Specific
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Services
import com.banking.backend.services.security.Argon2KDF;
import com.banking.backend.services.security.AuthenticationService;
import com.banking.backend.services.security.Encryptor;

//DTOs
import com.banking.backend.dto.access.AccessToken;
import com.banking.backend.dto.authentication.LoginRequestDTO;

//DAOs
import com.banking.backend.dao.accounts.AccountDAO;
import com.banking.backend.dao.logins.LoginDao;
import com.banking.backend.dao.sessions.ActiveSessionsDao;
import com.banking.backend.dao.users.UsersDao;

//Domains
import com.banking.backend.domain.accounts.Account;
import com.banking.backend.domain.users.User;

//Utils
import java.util.Base64;
import java.util.ArrayList;
import java.util.Optional;
import lombok.AllArgsConstructor;
import javax.crypto.SecretKey;

@Service
@AllArgsConstructor
public class LoginService {
    AuthenticationService authenticationService;
    Encryptor encryptor;
    ActiveSessionsDao activeSessionsDao;
    Argon2KDF argon2KDF;

    UsersDao userDao;
    LoginDao loginDao;
    AccountDAO accountDao;

    // Main
    @Transactional
    public Optional<Object> login(LoginRequestDTO loginRequest) {
        final String passOnRecord = getUserPassHash(loginRequest.getEmail());
        if (passOnRecord == null) {
            return Optional.of(404);
        }

        if (!authenticationService.verifyPass(passOnRecord, loginRequest.getPassword())) {
            return Optional.of(401);
        }
        User user = getBaseUser(loginRequest.getEmail());
        if (user == null) {
            return Optional.of(404);
        }
        setAccountsForUser(user);
        user.splitName(decryptUserName(user, loginRequest.getPassword()));
        final long loginId = this.loginDao.login(user.getUserID()).get();
        final String sessionToken = Base64.getEncoder().encodeToString(this.argon2KDF.getRandom(16));
        this.activeSessionsDao.addActiveSession(sessionToken, loginId);
        AccessToken accessToken = new AccessToken(sessionToken, user.getName(), user.getFamilyName(),
                user.getAllAccounts());
        return Optional.of(accessToken);
    }

    // Helpers
    private String getUserPassHash(String email) {
        Optional<String> passOnRecord = this.userDao.checkForUserByEmail(email);
        if (passOnRecord.isEmpty()) {
            return null;
        }
        return passOnRecord.get();
    }

    private User getBaseUser(String email) {
        Optional<User> user = this.userDao.getUserByEmail(email);
        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    private ArrayList<Account> getAccounts(long userId) {
        return new ArrayList<>(this.accountDao.getAccountsByUserID(userId));
    }

    private void setAccountsForUser(User user) {
        long id = user.getUserID();
        ArrayList<Account> accounts = getAccounts(id);
        user.setAccounts(accounts);
    }

    private String decryptUserName(User user, char[] password) {
        final String salt = this.userDao.getSalt(user.getUserID());
        final String iv = this.userDao.getIV(user.getUserID());
        final SecretKey decryptionKey = this.argon2KDF.deriveKey(password, Base64.getDecoder().decode(salt));
        final String decryptedName = this.encryptor.decryptWithAESGCM(user.getEncryptedName(),
                Base64.getDecoder().decode(iv), decryptionKey);
        return decryptedName;
    }
}

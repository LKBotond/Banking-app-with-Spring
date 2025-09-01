package com.banking.backend.services.access.impl;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.backend.services.security.AuthenticationService;
import com.banking.backend.dao.accounts.AccountDAO;
import com.banking.backend.accounts.Account;
import com.banking.backend.dao.deletion.DeletionDao;
import com.banking.backend.dao.logins.LoginDao;
import com.banking.backend.dao.saltsAndIVs.SaltsAndIvsDao;
import com.banking.backend.dao.users.UsersDao;
import com.banking.backend.services.access.AccessService;
import com.banking.backend.services.security.Argon2KDF;
import com.banking.backend.services.security.Encryptor;

@Service
public class AccessServiceImpl implements AccessService {

    // authentication
    private final AuthenticationService authenticationService;
    private final Argon2KDF argon2KDF;
    private final Encryptor encryptor;

    // fetchers
    private final UsersDao usersDao;
    private final AccountDAO accountDao;
    private final LoginDao loginDao;
    private final SaltsAndIvsDao saltsAndIvsDao;
    private final DeletionDao deletionDao;

    public AccessServiceImpl(
            AuthenticationService authenticationService,
            Argon2KDF argon2KDF,
            Encryptor encryptor,
            UsersDao userDao,
            AccountDAO accountDao,
            LoginDao loginDao,
            SaltsAndIvsDao saltsAndIvsDao,
            DeletionDao deletionDao) {
        this.authenticationService = authenticationService;
        this.argon2KDF = argon2KDF;
        this.encryptor = encryptor;
        this.usersDao = userDao;
        this.accountDao = accountDao;
        this.loginDao = loginDao;
        this.saltsAndIvsDao = saltsAndIvsDao;
        this.deletionDao = deletionDao;
    }

    @Override
    public boolean login(String email, char[] password) {

        Optional<String> passOnRecord = usersDao.checkForUserByEmail(email);
        if (passOnRecord.isEmpty()) {
            return false;
        }

        String exists = passOnRecord.get();
        if (authenticationService.verifyPass(exists, password)) {
            // need one dao method that gets me just the user id
            loginDao.login(0);
            return true;
        }

        return false;
    }

    // need login dao sigh
    @Override
    public void logout(long sessionID) {
        loginDao.logout(sessionID);
    }

    @Override
    public void register(String email, String fullName, char[] password) {

        byte[] salt = argon2KDF.getRandom(16);
        byte[] iv = argon2KDF.getRandom(12);
        SecretKey encryptionKey = argon2KDF.deriveKey(password.clone(), salt);
        String encryptedName = encryptor.encryptWithAESGCM(fullName, encryptionKey, iv);

        String passHash = authenticationService.hashWithArgon2(password.clone());

        Long userID = usersDao.create(email, encryptedName, passHash);
        if (userID == null) {
            throw new RuntimeException("Registration failed");
        }

        String storableSalt = Base64.getEncoder().encodeToString(salt);
        String storableIV = Base64.getEncoder().encodeToString(iv);
        saltsAndIvsDao.createUserField(userID, storableSalt, storableIV);
    }

    // neeed delete dao
    @Override
    @Transactional
    public void delete(long userID, char[] password) {
        String passOnRecord = usersDao.getPassHashByID(userID);

        if (!authenticationService.verifyPass(passOnRecord, password)) {
            throw new RuntimeException("Invalid password my friend");
        }
        List<Account> accounts = accountDao.getAccountsByUserID(userID);
        for (Account account : accounts) {
            deletionDao.deleteAccount(account.getAccounID());
        }
        deletionDao.deleteUserCrypto(userID);
        deletionDao.deleteUser(userID);
    }

}

package com.banking.backend.services.access;


//Spring Specific
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//DAOs
import com.banking.backend.dao.logins.LoginDao;
import com.banking.backend.dao.users.UsersDao;

//DTOs
import com.banking.backend.dto.access.AccessToken;
import com.banking.backend.dto.authentication.RegisterRequestDTO;
import com.banking.backend.exceptions.RegistrationFailedException;
import com.banking.backend.exceptions.UserAlreadyExistsException;
//services
import com.banking.backend.services.security.Argon2KDF;
import com.banking.backend.services.security.AuthenticationService;
import com.banking.backend.services.security.Encryptor;
import com.banking.backend.services.session.SessionService;

//Utils
import lombok.AllArgsConstructor;
import javax.crypto.SecretKey;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AuthenticationService authenticationService;
    private final SessionService sessionService;
    private final Argon2KDF argon2KDF;
    private final Encryptor encryptor;

    private final UsersDao usersDao;
    private final LoginDao loginDao;

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    @Transactional
    public AccessToken register(RegisterRequestDTO request) {
        log.info("got registration request");
        if (checkForExistingUser(request)) {
            log.info("User is already in the database");
            throw new UserAlreadyExistsException("Email is taken");
        }

        byte[] salt = this.argon2KDF.getRandom(16);
        byte[] iv = this.argon2KDF.getRandom(12);
        try {
            SecretKey encryptionKey = deriveEncryptionKey(request.getPassword(), salt);
            String encryptedName = encryptFullName(request, encryptionKey, iv);
            String passHash = hashPass(request);
            Long userId = createUserInDb(request, encryptedName, salt, iv, passHash);
            Long loginId = createLoginEntry(userId);
            AccessToken accessToken = createAccessToken(request, loginId);
            log.info("returning access token");
            return accessToken;
        } finally {
            authenticationService.wipeSensitiveMemory(salt, iv, request.getPassword());
        }

    }

    private SecretKey deriveEncryptionKey(char[] password, byte[] salt) {
        return argon2KDF.deriveKey(password.clone(), salt);
    }

    private String encryptFullName(RegisterRequestDTO request, SecretKey key, byte[] iv) {
        String fullName = String.join(" ", request.getFirstName(), request.getLastName());
        return encryptor.encryptWithAESGCM(fullName, key, iv);
    }

    private String hashPass(RegisterRequestDTO request) {
        return authenticationService.hashWithArgon2(request.getPassword());
    }

    private Long createUserInDb(RegisterRequestDTO request, String encryptedName, byte[] salt, byte[] iv,
            String passHash) {
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encodedIV = Base64.getEncoder().encodeToString(iv);

        return usersDao.create(request.getEmail(), encryptedName, encodedSalt, encodedIV, passHash)
                .orElseThrow(RegistrationFailedException::new);
    }

    private Long createLoginEntry(Long userId) {
        return this.loginDao.login(userId).orElseThrow(RegistrationFailedException::new);
    }

    private AccessToken createAccessToken(RegisterRequestDTO registerRequest, Long loginId) {
        AccessToken accessToken = new AccessToken(null, registerRequest.getFirstName(),
                registerRequest.getLastName(), null);
        this.sessionService.createSessionToken(accessToken, loginId);
        return accessToken;
    }

    private boolean checkForExistingUser(RegisterRequestDTO request) {
        return usersDao.getUserByEmail(request.getEmail()).isPresent();
    }
}

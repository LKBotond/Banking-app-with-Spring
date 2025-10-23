package com.banking.backend.services.access.subservices;

//Spring Specific
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//DAOs
import com.banking.backend.dao.logins.LoginDao;
import com.banking.backend.dao.sessions.ActiveSessionsDao;
import com.banking.backend.dao.users.UsersDao;

//DTOs
import com.banking.backend.dto.access.AccessToken;
import com.banking.backend.dto.authentication.RegisterRequestDTO;

//services
import com.banking.backend.services.security.Argon2KDF;
import com.banking.backend.services.security.AuthenticationService;
import com.banking.backend.services.security.Encryptor;

//Utils
import lombok.AllArgsConstructor;
import javax.crypto.SecretKey;
import java.util.Optional;
import java.util.Base64;

@Service
@AllArgsConstructor
public class RegistrationService {
    Argon2KDF argon2KDF;
    AuthenticationService authenticationService;
    Encryptor encryptor;

    UsersDao usersDao;
    ActiveSessionsDao activeSessionsDao;
    LoginDao loginDao;

    @Transactional
    public Optional<Object> register(RegisterRequestDTO registerRequest) {
        String fullName = (registerRequest.getFirstName() + " " + registerRequest.getLastName());

        byte[] salt = this.argon2KDF.getRandom(16);
        byte[] iv = this.argon2KDF.getRandom(12);
        SecretKey encryptionKey = this.argon2KDF.deriveKey(registerRequest.getPassword().clone(), salt);

        String encryptedName = this.encryptor.encryptWithAESGCM(fullName, encryptionKey, iv);

        String passHash = authenticationService.hashWithArgon2(registerRequest.getPassword().clone().clone());
        String storableSalt = Base64.getEncoder().encodeToString(salt);
        String storableIV = Base64.getEncoder().encodeToString(iv);
        Long userID = this.usersDao.create(registerRequest.getEmail(), encryptedName, storableSalt, storableIV,
                passHash);
        if (userID == null) {
            throw new RuntimeException("Registration failed");
        }
        long loginId = this.loginDao.login(userID).get();

        String sessionToken = Base64.getEncoder().encodeToString(this.argon2KDF.getRandom(16));

        this.activeSessionsDao.addActiveSession(sessionToken, loginId);

        AccessToken accessToken = new AccessToken(sessionToken, registerRequest.getFirstName(),
                registerRequest.getLastName(), null);
        return Optional.of(accessToken);
    }
}

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
//services
import com.banking.backend.services.security.Argon2KDF;
import com.banking.backend.services.security.AuthenticationService;
import com.banking.backend.services.security.Encryptor;
import com.banking.backend.services.session.SessionService;

//Utils
import lombok.AllArgsConstructor;
import javax.crypto.SecretKey;
import java.util.Base64;

@Service
@AllArgsConstructor
public class RegistrationService {
    AuthenticationService authenticationService;
    SessionService sessionService;
    Argon2KDF argon2KDF;
    Encryptor encryptor;

    UsersDao usersDao;
    LoginDao loginDao;

    @Transactional
    public AccessToken register(RegisterRequestDTO registerRequest) {
        String fullName = (registerRequest.getFirstName() + " " + registerRequest.getLastName());

        byte[] salt = this.argon2KDF.getRandom(16);
        byte[] iv = this.argon2KDF.getRandom(12);
        SecretKey encryptionKey = this.argon2KDF.deriveKey(registerRequest.getPassword().clone(), salt);

        String encryptedName = this.encryptor.encryptWithAESGCM(fullName, encryptionKey, iv);

        String passHash = authenticationService.hashWithArgon2(registerRequest.getPassword().clone().clone());
        String storableSalt = Base64.getEncoder().encodeToString(salt);
        String storableIV = Base64.getEncoder().encodeToString(iv);
        Long userID = this.usersDao.create(registerRequest.getEmail(), encryptedName, storableSalt, storableIV,
                passHash).orElseThrow(RegistrationFailedException::new);
        Long loginId = this.loginDao.login(userID).orElseThrow(RegistrationFailedException::new);

        AccessToken accessToken = createAccessToken(registerRequest, loginId);
        return accessToken;
    }

    private AccessToken createAccessToken(RegisterRequestDTO registerRequest, Long loginId) {
        AccessToken accessToken = new AccessToken(null, registerRequest.getFirstName(),
                registerRequest.getLastName(), null);
        this.sessionService.createSessionToken(accessToken, loginId);
        return accessToken;
    }
}

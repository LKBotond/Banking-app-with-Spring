package com.banking.backend.services.access;

//Spring Specific
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//DTOs
import com.banking.backend.dto.authentication.DeletionRequestDTO;
import com.banking.backend.dto.authentication.LoginRequestDTO;
import com.banking.backend.dto.authentication.LogoutRequestDTO;
import com.banking.backend.dto.authentication.RegisterRequestDTO;

//Subservices
import com.banking.backend.services.access.subservices.DeletionService;
import com.banking.backend.services.access.subservices.LoginService;
import com.banking.backend.services.access.subservices.LogoutService;
import com.banking.backend.services.access.subservices.RegistrationService;

//Utils
import lombok.AllArgsConstructor;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccessService {

    // Subservices
    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final DeletionService deletionService;
    private final LogoutService logoutService;

    public Optional<Object> register(RegisterRequestDTO registerRequest) {
        return this.registrationService.register(registerRequest);
    }

    public Optional<Object> login(LoginRequestDTO loginRequest) {
        return loginService.login(loginRequest);
    }

    @Transactional
    public Optional<Object> logout(LogoutRequestDTO logoutRequest) {
        return logoutService.logout(logoutRequest);
    }

    public Optional<Object> delete(DeletionRequestDTO deletionRequest) {
        return deletionService.deleteUser(deletionRequest);
    }
}

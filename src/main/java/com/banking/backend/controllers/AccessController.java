package com.banking.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.backend.dto.access.AccessToken;
import com.banking.backend.dto.authentication.DeletionRequestDTO;
import com.banking.backend.dto.authentication.LoginRequestDTO;
import com.banking.backend.dto.authentication.LogoutRequestDTO;
import com.banking.backend.dto.authentication.RegisterRequestDTO;
import com.banking.backend.responses.StatusResponse;
import com.banking.backend.services.access.DeletionService;
import com.banking.backend.services.access.LoginService;
import com.banking.backend.services.access.LogoutService;
import com.banking.backend.services.access.RegistrationService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/access")
@AllArgsConstructor
public class AccessController {
    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final LogoutService logoutService;
    private final DeletionService deletionService;

    private static final Logger log = LoggerFactory.getLogger(AccessController.class);

    @PostMapping("/register")
    public ResponseEntity<AccessToken> registerUser(@RequestBody RegisterRequestDTO registrationRequest) {
        log.info("Register endpoint called");
        AccessToken accessToken = registrationService.register(registrationRequest);
        log.info("Got accessToken");
        return ResponseEntity.ok(accessToken);
    }

    @PostMapping("/login")
    public ResponseEntity<AccessToken> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        log.info("Login endpoint called");
        AccessToken accessToken = loginService.login(loginRequest);
        log.info("Got accessToken");
        return ResponseEntity.ok(accessToken);
    }

    @PostMapping("/delete")
    public ResponseEntity<StatusResponse> deleteUser(@RequestBody DeletionRequestDTO deletionRequest) {
        log.info("Delete user called");
        deletionService.deleteUser(deletionRequest);
        return ResponseEntity.ok(new StatusResponse(true));
    }

    @PostMapping("/logout")
    public ResponseEntity<StatusResponse> logoutUser(@RequestBody AccessToken accessToken) {
        log.info("AccessToken: {}", accessToken);
        LogoutRequestDTO logoutRequest = new LogoutRequestDTO(accessToken.getSessionToken());
        logoutService.logout(logoutRequest);
        return ResponseEntity.ok(new StatusResponse(true));
    }
}

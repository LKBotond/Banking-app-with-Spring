package com.banking.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.backend.dto.access.AccessToken;
import com.banking.backend.dto.authentication.DeletionRequestDTO;
import com.banking.backend.dto.authentication.LoginRequestDTO;
import com.banking.backend.dto.authentication.LogoutRequestDTO;
import com.banking.backend.dto.authentication.RegisterRequestDTO;
import com.banking.backend.exceptions.DataBaseAccessException;
import com.banking.backend.exceptions.LoginIdNotFoundException;
import com.banking.backend.exceptions.UserAlreadyExistsException;
import com.banking.backend.exceptions.UserNotFoundException;
import com.banking.backend.exceptions.WrongPasswordException;
import com.banking.backend.responses.StatusResponse;
import com.banking.backend.services.access.DeletionService;
import com.banking.backend.services.access.LoginService;
import com.banking.backend.services.access.LogoutService;
import com.banking.backend.services.access.RegistrationService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
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
        try {
            AccessToken accessToken = registrationService.register(registrationRequest);
            log.info("Got accessToken");
            return ResponseEntity.ok(accessToken);
        } catch (UserAlreadyExistsException exception) {
            log.info("Email already in database");
            return ResponseEntity.badRequest().build();
        } catch (DataBaseAccessException e) {
            log.info("Database can't be reached because: {}", e);
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            log.info("Something went wrong: {}", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AccessToken> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        log.info("Login endpoint called");
        try {
            AccessToken accessToken = loginService.login(loginRequest);
            log.info("Got accessToken");
            return ResponseEntity.ok(accessToken);
        } catch (WrongPasswordException e) {
            log.info("Wrong password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (LoginIdNotFoundException e) {
            log.info("Database can't be reached");
            return ResponseEntity.internalServerError().build();
        } catch (UserNotFoundException e) {
            log.info("User not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<StatusResponse> deleteUser(@RequestBody DeletionRequestDTO deletionRequest) {
        try {
            log.info("try block reached");
            deletionService.deleteUser(deletionRequest);
            return ResponseEntity.ok(new StatusResponse(true));
        } catch (Exception e) {
            log.info("something went wrong :{}", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<StatusResponse> logoutUser(@RequestBody AccessToken accessToken) {
        log.info("AccessToken: {}", accessToken);
        LogoutRequestDTO logoutRequest = new LogoutRequestDTO(accessToken.getSessionToken());
        log.info("Got request: {}", logoutRequest);
        try {
            logoutService.logout(logoutRequest);
            return ResponseEntity.ok(new StatusResponse(true));
        } catch (Exception e) {
            log.info("exception: {}", e);
            return ResponseEntity.internalServerError().build();
        }
    }

}

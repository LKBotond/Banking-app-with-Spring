package com.banking.backend.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.backend.domain.accounts.Account;
import com.banking.backend.dto.access.AccessToken;
import com.banking.backend.exceptions.AccountNotFoundException;
import com.banking.backend.exceptions.DataBaseAccessException;
import com.banking.backend.exceptions.InvalidSessionException;
import com.banking.backend.services.account.AccountService;
import com.banking.backend.services.session.SessionService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final SessionService sessionService;
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody AccessToken accessToken) {
        if (!sessionService.validateSession(accessToken.getSessionToken())) {
            log.info("sessionToken validated: {}", accessToken);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            log.info("Creation try chain called");
            long userId = sessionService.getUserIdBySession(accessToken.getSessionToken());
            log.info("userID:{}", userId);
            Account account = accountService.createAccount(userId);
            log.info("Account id:{}", account.getAccountID());
            return ResponseEntity.ok().body(account);
        } catch (DataBaseAccessException e) {
            log.error("Database access failed", e);
            return ResponseEntity.internalServerError().build();
        } catch (InvalidSessionException e) {
            log.error("Invalid session", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/getAccount")
    public ResponseEntity<List<Account>> getAccounts(@RequestBody AccessToken accessToken) {
        log.info("get Account chain called");
        if (!sessionService.validateSession(accessToken.getSessionToken())) {
            log.info("sessionToken validated: {}", accessToken.getSessionToken());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            log.info("getAccount try Chain reached");
            long userId = sessionService.getUserIdBySession(accessToken.getSessionToken());
            log.info("got UserID: {}", userId);
            List<Account> accounts = accountService.getAccounts(userId);
            log.info("got accounts: {}", accounts);
            return ResponseEntity.ok().body(accounts);
        } catch (DataBaseAccessException e) {
            log.error("Something went wrong with the db", e);
            return ResponseEntity.internalServerError().build();
        } catch (InvalidSessionException e) {
            log.error("Invalid session", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (AccountNotFoundException e) {
            log.error("Account Not Found", e);
            return ResponseEntity.ok().body(null);
        }
    }
}

package com.banking.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.backend.domain.accounts.Account;
import com.banking.backend.exceptions.DataBaseAccessException;
import com.banking.backend.exceptions.InvalidSessionException;
import com.banking.backend.services.account.AccountService;
import com.banking.backend.services.session.SessionService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final SessionService sessionService;

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody String sessionToken) {
        if (!sessionService.validateSession(sessionToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            long userId = sessionService.getUserIdBySession(sessionToken);
            Account account = accountService.createAccount(userId);
            return ResponseEntity.ok().body(account);
        } catch (DataBaseAccessException e) {
            return ResponseEntity.internalServerError().build();
        } catch (InvalidSessionException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/getAccount")
    public ResponseEntity<List<Account>> getAccounts(@RequestParam String sessionToken) {
        if (!sessionService.validateSession(sessionToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            long userId = sessionService.getUserIdBySession(sessionToken);
            List<Account> accounts = accountService.getAccounts(userId);
            return ResponseEntity.ok().body(accounts);
        } catch (DataBaseAccessException e) {
            return ResponseEntity.internalServerError().build();
        } catch (InvalidSessionException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}

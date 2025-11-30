package com.banking.backend.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.backend.domain.accounts.Account;
import com.banking.backend.dto.access.AccessToken;
import com.banking.backend.dto.transaction.OperationDTO;
import com.banking.backend.dto.transaction.TransferDTO;
import com.banking.backend.services.account.AccountService;
import com.banking.backend.services.session.SessionService;
import com.banking.backend.services.transactions.impl.TransactionServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {

    private final TransactionServiceImpl transactionServiceImpl;
    private final SessionService sessionService;
    private final AccountService accountService;
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody AccessToken accessToken) {
        sessionService.validateSession(accessToken.getSessionToken());

        log.info("Creating account");
        long userId = sessionService.getUserIdBySession(accessToken.getSessionToken());
        log.info("userID:{}", userId);
        Account account = accountService.createAccount(userId);
        log.info("Account id:{}", account.getAccountID());
        return ResponseEntity.ok().body(account);
    }

    @PostMapping("/getAccounts")
    public ResponseEntity<List<Account>> getAccounts(@RequestBody AccessToken accessToken) {
        log.info("get Account chain called");

        sessionService.validateSession(accessToken.getSessionToken());
        log.info("getAccount try Chain reached");
        long userId = sessionService.getUserIdBySession(accessToken.getSessionToken());
        log.info("got UserID: {}", userId);
        List<Account> accounts = accountService.getAccounts(userId);
        log.info("got accounts: {}", accounts);
        return ResponseEntity.ok().body(accounts);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Account> deposit(@RequestBody OperationDTO depositRequest) {
        log.info("got deposit request: {}", depositRequest);
        sessionService.validateSession(depositRequest.getSessionToken());
        Account updated = transactionServiceImpl.deposit(depositRequest.getAccountId(), depositRequest.getSum());
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Account> withdraw(@RequestBody OperationDTO withdrawalRequest) {
        sessionService.validateSession(withdrawalRequest.getSessionToken());
        Account updated = transactionServiceImpl.withdraw(withdrawalRequest.getAccountId(), withdrawalRequest.getSum());
        return ResponseEntity.ok().body(updated);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Account> transfer(@RequestBody TransferDTO transferRequest) {
        sessionService.validateSession(transferRequest.getSessionToken());
        Account updatedSender = transactionServiceImpl.transaction(transferRequest.getSender(),
                transferRequest.getReceiver(),
                transferRequest.getSum());
        return ResponseEntity.ok().body(updatedSender);
    }

}

package dev.igor.apiaccount.api.controller;

import dev.igor.apiaccount.api.request.AccountRequest;
import dev.igor.apiaccount.api.response.AccountResponse;
import dev.igor.apiaccount.model.Account;
import dev.igor.apiaccount.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request) {
        return ResponseEntity.ok(service.createAccount(request));
    }
}

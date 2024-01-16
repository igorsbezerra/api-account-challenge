package dev.igor.apiaccount.api.controller;

import dev.igor.apiaccount.api.request.AccountRequest;
import dev.igor.apiaccount.api.response.AccountAvailableBalanceResponse;
import dev.igor.apiaccount.api.response.AccountResponse;
import dev.igor.apiaccount.service.AccountService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAccount(request));
    }

    @GetMapping("/{accountCode}")
    public ResponseEntity<AccountResponse> findByAccountCode(@PathVariable("accountCode") String accountCode) {
        return ResponseEntity.ok(service.findAccountByAccountCode(accountCode));
    }

    @GetMapping("/available-balance")
    public ResponseEntity<AccountAvailableBalanceResponse> availableBalance(
            @RequestParam("accountCode") String accountCode,
            @RequestParam("amount") String amount) {
        return ResponseEntity.ok(service.availableBalance(accountCode, amount));
    }
}

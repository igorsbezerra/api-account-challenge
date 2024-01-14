package dev.igor.apiaccount.api.controller;

import dev.igor.apiaccount.api.request.AccountRequest;
import dev.igor.apiaccount.api.response.AccountResponse;
import dev.igor.apiaccount.service.AccountService;
import jakarta.validation.Valid;
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
        return ResponseEntity.ok(service.createAccount(request));
    }

    @GetMapping("/{accountCode}")
    public ResponseEntity<AccountResponse> findByAccountCode(@PathVariable("accountCode") String accountCode) {
        return ResponseEntity.ok(service.findAccountByAccountCode(accountCode));
    }

    @GetMapping("/available-balance")
    public ResponseEntity<Object> availableBalance(
            @RequestParam("accountCode") String accountCode,
            @RequestParam("amount") String amount) {
        return ResponseEntity.ok(service.availableBalance(accountCode, amount));
    }
}

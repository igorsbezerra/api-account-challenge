package dev.igor.apiaccount.service;

import dev.igor.apiaccount.api.request.AccountRequest;
import dev.igor.apiaccount.api.response.AccountResponse;
import dev.igor.apiaccount.model.Account;

public interface AccountService {
    AccountResponse createAccount(AccountRequest request);
}

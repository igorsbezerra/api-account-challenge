package dev.igor.apiaccount.service;

import dev.igor.apiaccount.api.request.AccountRequest;
import dev.igor.apiaccount.api.response.AccountAvailableBalanceResponse;
import dev.igor.apiaccount.api.response.AccountResponse;

public interface AccountService {
    AccountResponse createAccount(AccountRequest request);
    AccountResponse findAccountByAccountCode(String accountCode);
    AccountAvailableBalanceResponse availableBalance(String accountCode, String value);
}

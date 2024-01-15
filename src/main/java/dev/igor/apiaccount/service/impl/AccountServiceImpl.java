package dev.igor.apiaccount.service.impl;

import dev.igor.apiaccount.api.request.AccountRequest;
import dev.igor.apiaccount.api.response.AccountAvailableBalanceResponse;
import dev.igor.apiaccount.api.response.AccountResponse;
import dev.igor.apiaccount.client.UserClient;
import dev.igor.apiaccount.dto.UserDTO;
import dev.igor.apiaccount.error.AccountNotFoundException;
import dev.igor.apiaccount.model.Account;
import dev.igor.apiaccount.repository.AccountRepository;
import dev.igor.apiaccount.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {
    private final UserClient userClient;
    private final AccountRepository repository;

    public AccountServiceImpl(UserClient userClient, AccountRepository repository) {
        this.userClient = userClient;
        this.repository = repository;
    }

    @Override
    public AccountResponse createAccount(AccountRequest request) {
        UserDTO user = this.userClient.findByDocument(request.getDocument());
        String accountCode = this.gen();
        Account accountCreate = Account.create(accountCode, request.getAgency(), user.getId());
        Account account = repository.save(accountCreate);
        return AccountResponse.of(account);
    }

    @Override
    public AccountResponse findAccountByAccountCode(String accountCode) {
        Optional<Account> account = this.findByAccountCode(accountCode);
        if (account.isEmpty()) {
            throw new AccountNotFoundException();
        }
        return AccountResponse.of(account.get());
    }

    @Override
    public AccountAvailableBalanceResponse availableBalance(String accountCode, String value) {
        Optional<Account> account = this.findByAccountCode(accountCode);
        if (account.isEmpty()) {
            throw new AccountNotFoundException();
        }

        return new AccountAvailableBalanceResponse(account.get().getAccountBalance().doubleValue() >= Double.parseDouble(value));
    }

    private String gen() {
        Random r = new Random( System.currentTimeMillis() );
        return String.valueOf((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    private Optional<Account> findByAccountCode(String accountCode) {
        return repository.findByAccountCode(accountCode);
    }
}

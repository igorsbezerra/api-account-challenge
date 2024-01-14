package dev.igor.apiaccount.service.impl;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import dev.igor.apiaccount.dto.Transaction;
import dev.igor.apiaccount.model.Account;
import dev.igor.apiaccount.repository.AccountRepository;
import dev.igor.apiaccount.service.TransactionService;

@Service
public class OutcomeServiceImpl implements TransactionService {
    private final AccountRepository repository;

    public OutcomeServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void outcome(Transaction transaction) {
        Optional<Account> account = repository.findById(transaction.getSourceAccount());
        if (account.isPresent()) {
            account.get().setAccountBalance(account.get().getAccountBalance().subtract(new BigDecimal(transaction.getAmount())));
            repository.save(account.get());
        }
    }

    @Override
    public void income(Transaction transaction) {
        Optional<Account> account = repository.findById(transaction.getSourceAccount());
        if (account.isPresent()) {
            account.get().setAccountBalance(account.get().getAccountBalance().add(new BigDecimal(transaction.getAmount())));
            repository.save(account.get());
        }
    }
}

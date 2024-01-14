package dev.igor.apiaccount.service;

import dev.igor.apiaccount.dto.Transaction;

public interface TransactionService {
    void outcome(Transaction transaction);
    void income(Transaction transaction);
}

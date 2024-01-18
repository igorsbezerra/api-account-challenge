package dev.igor.apiaccount.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.igor.apiaccount.dto.Transaction;
import dev.igor.apiaccount.model.Account;
import dev.igor.apiaccount.model.enums.AccountStatus;
import dev.igor.apiaccount.repository.AccountRepository;
import dev.igor.apiaccount.service.impl.TransactionServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private TransactionServiceImpl service;

    @Test
    void it_must_be_possible_to_increase_the_account_balance() {
        final var expectedValue = new BigDecimal("1100");
        Transaction transaction = createTransaction();
        Account account = createAccount();
        Mockito.when(accountRepository.findById(Mockito.anyString())).thenReturn(Optional.of(account));

        service.income(transaction);

        Assertions.assertEquals(expectedValue, account.getAccountBalance());
    }

    @Test
    void it_must_be_possible_to_subtract_account_balance() {
        final var expectedValue = new BigDecimal("900");
        Transaction transaction = createTransaction();
        Account account = createAccount();
        Mockito.when(accountRepository.findById(Mockito.anyString())).thenReturn(Optional.of(account));

        service.outcome(transaction);

        Assertions.assertEquals(expectedValue, account.getAccountBalance());
    }

    @Test
    void it_must_be_possible_to_devolution_accounts_balance() {
        final var expectedBalanceSource = new BigDecimal("1100");
        final var expectedBalanceTarget = new BigDecimal("900");
        Transaction transaction = createTransaction();
        Account accountSource = createSource();
        Account accountTarget = createTarget();
        Mockito.when(accountRepository.findById(accountSource.getAccountCode())).thenReturn(Optional.of(accountSource));
        Mockito.when(accountRepository.findById(accountTarget.getAccountCode())).thenReturn(Optional.of(accountTarget));

        service.devolution(transaction);

        Assertions.assertEquals(expectedBalanceSource, accountSource.getAccountBalance());
        Assertions.assertEquals(expectedBalanceTarget, accountTarget.getAccountBalance());
    }

    private Transaction createTransaction() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedSourceAccount = "123456";
        final var expectedTargetAccount = "654321";
        final var expectedAmount = "100";
        final var expectedType = "type";

        Transaction transaction = new Transaction();
        transaction.setId(expectedId);
        transaction.setSourceAccount(expectedSourceAccount);
        transaction.setTargetAccount(expectedTargetAccount);
        transaction.setAmount(expectedAmount);
        transaction.setType(expectedType);
        return transaction;
    }

    private Account createAccount() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedAccountCode = "123456";
        final var expectedAgency = "santander";
        final var expecetedUserId = UUID.randomUUID().toString();
        final var expectedBalance = new BigDecimal("1000");
        final var expectedStatus = AccountStatus.ACTIVE;

        Account account = new Account();
        account.setId(expectedId);
        account.setAccountCode(expectedAccountCode);
        account.setAgency(expectedAgency);
        account.setUserId(expecetedUserId);
        account.setAccountBalance(expectedBalance);
        account.setStatus(expectedStatus);
        return account;
    }

    private Account createSource() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedAccountCode = "123456";
        final var expectedAgency = "santander";
        final var expecetedUserId = UUID.randomUUID().toString();
        final var expectedBalance = new BigDecimal("1000");
        final var expectedStatus = AccountStatus.ACTIVE;

        Account account = new Account();
        account.setId(expectedId);
        account.setAccountCode(expectedAccountCode);
        account.setAgency(expectedAgency);
        account.setUserId(expecetedUserId);
        account.setAccountBalance(expectedBalance);
        account.setStatus(expectedStatus);
        return account;
    }

    private Account createTarget() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedAccountCode = "654321";
        final var expectedAgency = "santander";
        final var expecetedUserId = UUID.randomUUID().toString();
        final var expectedBalance = new BigDecimal("1000");
        final var expectedStatus = AccountStatus.ACTIVE;

        Account account = new Account();
        account.setId(expectedId);
        account.setAccountCode(expectedAccountCode);
        account.setAgency(expectedAgency);
        account.setUserId(expecetedUserId);
        account.setAccountBalance(expectedBalance);
        account.setStatus(expectedStatus);
        return account;
    }
}

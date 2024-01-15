package dev.igor.apiaccount.model;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.igor.apiaccount.model.enums.AccountStatus;

@ExtendWith(MockitoExtension.class)
public class TransactionTest {
    @Test
    void getter_and_setter() {
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

        Assertions.assertEquals(expectedId, account.getId());
        Assertions.assertEquals(expectedAccountCode, account.getAccountCode());
        Assertions.assertEquals(expectedAgency, account.getAgency());
        Assertions.assertEquals(expecetedUserId, account.getUserId());
        Assertions.assertEquals(expectedBalance, account.getAccountBalance());
        Assertions.assertEquals(expectedStatus, account.getStatus());

        Account account2 = new Account(expectedId, expectedAccountCode, expectedAgency, expecetedUserId, expectedBalance, expectedStatus);

        Assertions.assertEquals(expectedId, account2.getId());
        Assertions.assertEquals(expectedAccountCode, account2.getAccountCode());
        Assertions.assertEquals(expectedAgency, account2.getAgency());
        Assertions.assertEquals(expecetedUserId, account2.getUserId());
        Assertions.assertEquals(expectedBalance, account2.getAccountBalance());
        Assertions.assertEquals(expectedStatus, account2.getStatus());
    }

    void create_method() {
        final var expectedAccountCode = "123456";
        final var expectedAgency = "santander";
        final var expecetedUserId = UUID.randomUUID().toString();

        Account account = Account.create(expectedAccountCode, expectedAgency, expecetedUserId);

        Assertions.assertNotNull(account.getId());
        Assertions.assertEquals(expectedAccountCode, account.getAccountCode());
        Assertions.assertEquals(expectedAgency, account.getAgency());
        Assertions.assertEquals(expecetedUserId, account.getUserId());
        Assertions.assertNotNull(account.getStatus());
    }
}

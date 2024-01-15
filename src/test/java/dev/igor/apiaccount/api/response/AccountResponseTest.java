package dev.igor.apiaccount.api.response;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.igor.apiaccount.model.Account;
import dev.igor.apiaccount.model.enums.AccountStatus;

@ExtendWith(MockitoExtension.class)
public class AccountResponseTest {
    @Test
    void getter_and_setter() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedAccountCode = "123456";
        final var expectedUserId = UUID.randomUUID().toString();

        AccountResponse accountResponse = new AccountResponse(expectedId, expectedAccountCode, expectedUserId);

        Assertions.assertEquals(expectedId, accountResponse.getId());
        Assertions.assertEquals(expectedAccountCode, accountResponse.getAccountCode());
        Assertions.assertEquals(expectedUserId, accountResponse.getUserId());

        accountResponse.setId("1234");

        Assertions.assertEquals("1234", accountResponse.getId());
    }

    @Test
    void teste_create_method() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedAccountCode = "123456";
        final var expectedAgency = "santander";
        final var expecetedUserId = UUID.randomUUID().toString();
        final var expectedBalance = new BigDecimal("1000");
        final var expectedStatus = AccountStatus.ACTIVE;
        Account account = new Account(expectedId, expectedAccountCode, expectedAgency, expecetedUserId, expectedBalance, expectedStatus);

        AccountResponse response = AccountResponse.of(account);

        Assertions.assertEquals(expectedId, response.getId());
        Assertions.assertEquals(expectedAccountCode, response.getAccountCode());
        Assertions.assertEquals(expecetedUserId, response.getUserId());
    }
}

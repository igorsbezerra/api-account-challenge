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

import dev.igor.apiaccount.api.request.AccountRequest;
import dev.igor.apiaccount.api.response.AccountAvailableBalanceResponse;
import dev.igor.apiaccount.api.response.AccountResponse;
import dev.igor.apiaccount.client.UserClient;
import dev.igor.apiaccount.dto.UserDTO;
import dev.igor.apiaccount.error.AccountNotFoundException;
import dev.igor.apiaccount.model.Account;
import dev.igor.apiaccount.model.enums.AccountStatus;
import dev.igor.apiaccount.repository.AccountRepository;
import dev.igor.apiaccount.service.impl.AccountServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private UserClient userClient;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void should_be_possible_to_create_an_account_when_invoking_the_createAccount_method() {
        UserDTO usertDTO = createUsertDTO();
        Account account = createAccount();
        AccountRequest request = createRequest();
        Mockito.when(userClient.findByDocument(Mockito.anyString())).thenReturn(usertDTO);
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(account);

        AccountResponse result = accountService.createAccount(request);

        Assertions.assertEquals(account.getId(), result.getId());
        Assertions.assertEquals(account.getAccountCode(), result.getAccountCode());
        Assertions.assertEquals(account.getUserId(), result.getUserId());
    }

    @Test
    void should_be_possible_to_search_for_an_account_when_invoking_the_findByAccountCode_method() {
        Account account = createAccount();
        Mockito.when(accountRepository.findByAccountCode(Mockito.anyString())).thenReturn(Optional.of(account));

        AccountResponse result = accountService.findAccountByAccountCode("123456");

        Assertions.assertEquals(account.getId(), result.getId());
        Assertions.assertEquals(account.getAccountCode(), result.getAccountCode());
        Assertions.assertEquals(account.getUserId(), result.getUserId());
    }

    @Test
    void should_not_be_possible_to_search_for_an_account_when_invoking_the_findByAccountCode_method() {
        Mockito.when(accountRepository.findByAccountCode(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.findAccountByAccountCode("123456"));
    }

    @Test
    void should_be_possible_to_check_the_availability_of_the_account_when_invoking_the_availableBalance_method() {
        Account account = createAccount();
        Mockito.when(accountRepository.findByAccountCode(Mockito.anyString())).thenReturn(Optional.of(account));

        AccountAvailableBalanceResponse result = accountService.availableBalance("123456", "100");

        Assertions.assertTrue(Boolean.parseBoolean(result.getAvailable()));
    }

    @Test
    void should_not_be_possible_to_check_the_availability_of_the_account_when_invoking_the_availableBalance_method() {
        Mockito.when(accountRepository.findByAccountCode(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.availableBalance("123456", "100"));
    }

    private UserDTO createUsertDTO() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "name";
        final var expectedDocument = "document";

        UserDTO userDTO = new UserDTO();
        userDTO.setId(expectedId);
        userDTO.setName(expectedName);
        userDTO.setDocument(expectedDocument);
        return userDTO;
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

    private AccountRequest createRequest() {
        final var expectedDocument = "123456";
        final var expectedAgency = "santander";

        AccountRequest request = new AccountRequest();
        request.setDocument(expectedDocument);
        request.setAgency(expectedAgency);
        return request;
    }
}

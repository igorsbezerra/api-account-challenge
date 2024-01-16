package dev.igor.apiaccount.api.controller;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dev.igor.apiaccount.api.request.AccountRequest;
import dev.igor.apiaccount.api.response.AccountAvailableBalanceResponse;
import dev.igor.apiaccount.api.response.AccountResponse;
import dev.igor.apiaccount.service.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {
    @Mock
    private AccountService service;
    @InjectMocks
    private AccountController controller;

    @Test
    void should_be_possible_to_create_an_account_when_invoking_the_createAccount_method() {
        AccountResponse response = createResponse();
        AccountRequest request = createRequest();
        Mockito.when(service.createAccount(Mockito.any())).thenReturn(response);

        ResponseEntity<AccountResponse> account = controller.createAccount(request);

        Assertions.assertEquals(HttpStatus.CREATED, account.getStatusCode());
        Assertions.assertEquals(response.getId(), account.getBody().getId());
        Mockito.verify(service, Mockito.times(1)).createAccount(request);
    }

    @Test
    void should_be_possible_to_search_for_an_account_when_invoking_the_findByAccountCode_method() {
        AccountResponse response = createResponse();
        Mockito.when(service.findAccountByAccountCode(Mockito.anyString())).thenReturn(response);

        ResponseEntity<AccountResponse> result = controller.findByAccountCode("123456");

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(response.getId(), result.getBody().getId());
        Mockito.verify(service, Mockito.times(1)).findAccountByAccountCode("123456");
    }

    @Test
    void should_be_possible_to_check_the_availability_of_the_account_when_invoking_the_availableBalance_method() {
        AccountAvailableBalanceResponse response = new AccountAvailableBalanceResponse(true);
        Mockito.when(service.availableBalance(Mockito.anyString(), Mockito.anyString())).thenReturn(response);

        ResponseEntity<AccountAvailableBalanceResponse> result = controller.availableBalance("123456", "100");

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(response.getAvailable(), result.getBody().getAvailable());
        Mockito.verify(service, Mockito.times(1)).availableBalance("123456", "100");
    }

    private AccountRequest createRequest() {
        final var expectedDocument = "123456";
        final var expectedAgency = "santander";

        AccountRequest request = new AccountRequest();
        request.setDocument(expectedDocument);
        request.setAgency(expectedAgency);
        return request;
    }

    private AccountResponse createResponse() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedAccountCode = "123456";
        final var expectedUserId = UUID.randomUUID().toString();
        return new AccountResponse(expectedId, expectedAccountCode, expectedUserId);
    }

}

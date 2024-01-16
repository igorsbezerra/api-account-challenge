package dev.igor.apiaccount.api.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountRequestTest {
    @Test
    void getter_and_setter() {
        final var expectedDocument = "123456";
        final var expectedAgency = "santander";

        AccountRequest request = new AccountRequest();
        request.setDocument(expectedDocument);
        request.setAgency(expectedAgency);

        Assertions.assertEquals(expectedDocument, request.getDocument());
        Assertions.assertEquals(expectedAgency, request.getAgency());
    }
}

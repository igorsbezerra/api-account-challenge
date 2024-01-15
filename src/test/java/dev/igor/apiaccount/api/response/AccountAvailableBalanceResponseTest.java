package dev.igor.apiaccount.api.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountAvailableBalanceResponseTest {
    @Test
    void getter_and_setter() {
        final var expectedAvailable = true;
        final var expectedNotAvailable2 = "true";
        final var expectedNotAvailable = "false";

        AccountAvailableBalanceResponse response = new AccountAvailableBalanceResponse(expectedAvailable);

        Assertions.assertEquals(expectedNotAvailable2, response.getAvailable());

        AccountAvailableBalanceResponse response2 = new AccountAvailableBalanceResponse(expectedAvailable);
        response2.setAvailable(expectedNotAvailable);

        Assertions.assertEquals(expectedNotAvailable, response2.getAvailable());
    }
}

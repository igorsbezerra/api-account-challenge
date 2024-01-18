package dev.igor.apiaccount.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransactionDTOTest {
    @Test
    void getter_and_setter() {
        final var expectedSourceAccount = "123456";
        final var expectedTargetAccount = "654321";
        final var expectedAmount = "100";


        Transaction transaction = new Transaction();
        transaction.setSourceAccount(expectedSourceAccount);
        transaction.setTargetAccount(expectedTargetAccount);
        transaction.setAmount(expectedAmount);

        Assertions.assertEquals(expectedSourceAccount, transaction.getSourceAccount());
        Assertions.assertEquals(expectedTargetAccount, transaction.getTargetAccount());
        Assertions.assertEquals(expectedAmount, transaction.getAmount());
    }
}

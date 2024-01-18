package dev.igor.apiaccount.event;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.igor.apiaccount.dto.Transaction;
import dev.igor.apiaccount.service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionEventTest {
    @Mock
    private TransactionService service;
    @Mock
    private ObjectMapper mapper;
    @InjectMocks
    private TransactionEvent event;

    @Test
    void must_receive_event_and_direct_it_to_transaction_processing_income() throws JsonProcessingException {
        Transaction transaction = createTransaction();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(transaction);
        Mockito.when(mapper.readValue(Mockito.anyString(), Mockito.eq(Transaction.class))).thenReturn(transaction);

        Assertions.assertDoesNotThrow(() ->event.receiveIncome(json));
    }

    @Test
    void must_receive_event_and_direct_it_to_transaction_processing_outcome() throws JsonProcessingException {
        Transaction transaction = createTransaction();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(transaction);
        Mockito.when(mapper.readValue(Mockito.anyString(), Mockito.eq(Transaction.class))).thenReturn(transaction);

        Assertions.assertDoesNotThrow(() -> event.receiveOutcome(json));
    }

    @Test
    void must_receive_event_and_direct_it_to_transaction_processing_devolution() throws JsonProcessingException {
        Transaction transaction = createTransaction();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(transaction);
        Mockito.when(mapper.readValue(Mockito.anyString(), Mockito.eq(Transaction.class))).thenReturn(transaction);

        Assertions.assertDoesNotThrow(() -> event.receiveDevolution(json));
    }

    private Transaction createTransaction() {
        final var expectedSourceAccount = "123456";
        final var expectedTargetAccount = "654321";
        final var expectedAmount = "100";


        Transaction transaction = new Transaction();
        transaction.setSourceAccount(expectedSourceAccount);
        transaction.setTargetAccount(expectedTargetAccount);
        transaction.setAmount(expectedAmount);
        return transaction;
    }
}

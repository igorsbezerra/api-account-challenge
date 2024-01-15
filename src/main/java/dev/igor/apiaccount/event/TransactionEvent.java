package dev.igor.apiaccount.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.igor.apiaccount.dto.Transaction;
import dev.igor.apiaccount.service.TransactionService;

@Component
public class TransactionEvent {
    private final TransactionService service;
    private final ObjectMapper mapper;

    public TransactionEvent(ObjectMapper mapper, TransactionService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @RabbitListener(queues = {"queue-outcome"})
    public void receiveOutcome(@Payload String message) {
        Transaction transaction;
        try {
            transaction = mapper.readValue(message, Transaction.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to generate Object Java with message");
        }
        service.outcome(transaction);
    }

    @RabbitListener(queues = {"queue-income"})
    public void receiveIncome(@Payload String message) {
        Transaction transaction;
        try {
            transaction = mapper.readValue(message, Transaction.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to generate Object Java with message");
        }
        service.income(transaction);
    }
}   

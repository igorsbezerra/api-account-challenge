package dev.igor.apiaccount.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
    public void receiveOutcome(@Payload String message) throws JsonMappingException, JsonProcessingException {
        Transaction transaction = mapper.readValue(message, Transaction.class);
        service.outcome(transaction);
    }

    @RabbitListener(queues = {"queue-income"})
    public void receiveIncome(@Payload String message) throws JsonMappingException, JsonProcessingException {
        Transaction transaction = mapper.readValue(message, Transaction.class);
        service.income(transaction);
    }

    @RabbitListener(queues = {"queue-devolution"})
    public void receiveDevolution(@Payload String message) throws JsonMappingException, JsonProcessingException {
        Transaction transaction = mapper.readValue(message, Transaction.class);
        service.devolution(transaction);
    }
}   

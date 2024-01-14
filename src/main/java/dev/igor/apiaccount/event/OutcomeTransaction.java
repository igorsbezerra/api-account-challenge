package dev.igor.apiaccount.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class OutcomeTransaction {
    @RabbitListener(queues = {"file-teste"})
    public void receive(@Payload String message) {
        log.info(message);
    }
}   

package com.villager.batchserver.event.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.villager.batchserver.config.events.VillagerEvents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class VillagerEventConsumerService {

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "villager.queue")
    public void receiveMessage(final Message message) {
        try {
            VillagerEvent villagerEvent = objectMapper.readValue(message.getBody(), VillagerEvent.class);
            VillagerEvents.raise(villagerEvent);
        } catch (IOException e) {
            log.error("", e);
        }
    }
}

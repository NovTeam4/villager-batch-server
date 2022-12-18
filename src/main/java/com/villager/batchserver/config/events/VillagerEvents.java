package com.villager.batchserver.config.events;

import com.villager.batchserver.event.consumer.VillagerEvent;
import org.springframework.context.ApplicationEventPublisher;

public class VillagerEvents {
    private static ApplicationEventPublisher publisher;

    public static void setPublisher(ApplicationEventPublisher publisher) {
        VillagerEvents.publisher = publisher;
    }

    public static void raise(VillagerEvent event) {
        if(publisher != null) {
            publisher.publishEvent(event);
        }
    }
}
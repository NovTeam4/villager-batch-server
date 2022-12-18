package com.villager.batchserver.event.consumer;

import com.villager.batchserver.event.service.VillagerEventProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VillagerEventHandler {
    private final List<VillagerEventProcessService> eventServiceList;

    @Async
    @EventListener(VillagerEvent.class)
    public void handle(VillagerEvent event) {
        for (VillagerEventProcessService eventService : eventServiceList) {
            if(eventService.isSupport(event)) {
                eventService.process(event);
            }
        }
    }
}

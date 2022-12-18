package com.villager.batchserver.event.consumer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VillagerEvent {
    private VillagerEventType eventType;
    private long timestamp;
    private Object body;
}
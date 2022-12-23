package com.villager.batchserver.event.consumer;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VillagerEvent {
    private VillagerEventType eventType;
    private long timestamp;
    private Object body;
}
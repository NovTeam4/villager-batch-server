package com.villager.batchserver.event.service;


import com.villager.batchserver.event.consumer.VillagerEvent;

public interface VillagerEventProcessService {
    boolean isSupport(VillagerEvent event);
    void process(VillagerEvent event);
}

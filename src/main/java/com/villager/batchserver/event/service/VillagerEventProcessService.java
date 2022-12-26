package com.villager.batchserver.event.service;


import com.villager.batchserver.event.consumer.VillagerEvent;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public interface VillagerEventProcessService {
    boolean isSupport(VillagerEvent event);
    void process(VillagerEvent event);
}

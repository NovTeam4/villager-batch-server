package com.villager.batchserver.config.events;

import com.villager.batchserver.event.consumer.VillagerEvent;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;

import java.util.Map;

public interface VillagerEventConfig {
    boolean isSupport(VillagerEvent event);
    Job getJob();
    JobParameters createJobParameters(VillagerEvent event);
}

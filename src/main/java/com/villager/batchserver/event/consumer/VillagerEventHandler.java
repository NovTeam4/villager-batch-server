package com.villager.batchserver.event.consumer;

import com.villager.batchserver.config.events.VillagerEventConfig;
import com.villager.batchserver.event.service.VillagerEventProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class VillagerEventHandler {
    private final List<VillagerEventConfig> configs;
    private final JobLauncher jobLauncher;

    @Async
    @EventListener(VillagerEvent.class)
    public void handle(VillagerEvent event) {
        for (VillagerEventConfig config : configs) {
            if(config.isSupport(event)) {
                try {
                    jobLauncher.run(config.getJob(), config.createJobParameters(event));
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
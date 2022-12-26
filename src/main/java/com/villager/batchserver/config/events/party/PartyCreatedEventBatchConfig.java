package com.villager.batchserver.config.events.party;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.villager.batchserver.config.events.CustomJobParameter;
import com.villager.batchserver.config.events.VillagerEventConfig;
import com.villager.batchserver.event.body.PartyCreatedBody;
import com.villager.batchserver.event.consumer.VillagerEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.villager.batchserver.event.consumer.VillagerEventType.PARTY_CREATED_EVENT;

@Configuration
@RequiredArgsConstructor
public class PartyCreatedEventBatchConfig implements VillagerEventConfig {
    public static String PARTY_CREATED_EVENT_BATCH_CONFIG_KEY = "partyCreated";
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PartyCreatedEventProcessTasklet partyCreatedEventProcessTasklet;
    private final ObjectMapper mapper;

    @Bean
    public Job partyCreateEventBatchJob() {
        return jobBuilderFactory.get("party_created_event_job")
                .start(partyCreateEventStep())
                .build();
    }

    @Bean
    public Step partyCreateEventStep() {
        return stepBuilderFactory.get("party_created_event_context_step")
                .tasklet(partyCreatedEventProcessTasklet)
                .build();
    }

    @Override
    public boolean isSupport(VillagerEvent event) {
        return event.getEventType().equals(PARTY_CREATED_EVENT);
    }

    @Override
    public Job getJob() {
        return partyCreateEventBatchJob();
    }

    @Override
    public JobParameters createJobParameters(VillagerEvent event) {
        return new JobParametersBuilder()
                .addParameter(PARTY_CREATED_EVENT_BATCH_CONFIG_KEY, new CustomJobParameter<>(getBody(event)))
                .addParameter("time", new JobParameter(System.currentTimeMillis()))
                .toJobParameters();
    }

    private PartyCreatedBody getBody(VillagerEvent event) {
        return mapper.convertValue(event.getBody(), PartyCreatedBody.class);
    }
}

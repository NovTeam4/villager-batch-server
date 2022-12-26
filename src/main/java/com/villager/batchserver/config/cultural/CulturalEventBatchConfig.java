package com.villager.batchserver.config.cultural;

import com.villager.batchserver.cultural.domain.Cultural;
import com.villager.batchserver.cultural.dto.CulturalEventRow;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CulturalEventBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CulturalEventItemReader culturalEventItemReader;
    private final CulturalEventItemProcessor culturalEventItemProcessor;
    private final CulturalEventItemWriter<Cultural> culturalEventItemWriter;
    private final CulturalCleanupRepositoryTasklet culturalCleanupRepositoryTasklet;
    @Bean
    public Job culturalEventBatchJob() {
        return jobBuilderFactory.get("culturalEventJob")
                .start(culturalEventStep())
                .next(clearCulturalStep())
                .build();
    }

    @Bean
    public Step culturalEventStep() {
        return stepBuilderFactory.get("culturalEventStep")
                .<List<CulturalEventRow>, List<Cultural>>chunk(1)
                .reader(culturalEventItemReader)
                .processor(culturalEventItemProcessor)
                .writer(culturalEventItemWriter)
                .build();
    }

    @Bean
    public Step clearCulturalStep() {
        return stepBuilderFactory.get("cleanupCultural")
                .tasklet(culturalCleanupRepositoryTasklet)
                .build();
    }
}

package com.villager.batchserver;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableBatchProcessing
@EnableScheduling
@SpringBootApplication
public class VillagerBatchServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VillagerBatchServerApplication.class, args);
    }

}

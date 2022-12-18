package com.villager.batchserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class VillagerBatchServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VillagerBatchServerApplication.class, args);
    }

}

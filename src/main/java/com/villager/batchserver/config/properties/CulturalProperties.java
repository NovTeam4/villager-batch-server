package com.villager.batchserver.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cultural")
public class CulturalProperties {
    private String path;
    private String key;
    private String type;
    private String service;
    private int batchSize;
}

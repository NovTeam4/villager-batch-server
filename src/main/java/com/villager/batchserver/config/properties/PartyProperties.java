package com.villager.batchserver.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "party")
public class PartyProperties {

    private Notify notify = new Notify();
    private String name;

    @Getter
    @Setter
    public static class Notify {
        private int batchSize;
        private Long timeout;
    }

}

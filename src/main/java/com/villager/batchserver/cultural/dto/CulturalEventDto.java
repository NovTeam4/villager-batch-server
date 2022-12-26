package com.villager.batchserver.cultural.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CulturalEventDto {

    @Getter
    @Setter
    public static class ResponseCulturalEventApi {
        @JsonProperty("culturalEventInfo")
        private CulturalEventInfo culturalEventInfo;
    }
}

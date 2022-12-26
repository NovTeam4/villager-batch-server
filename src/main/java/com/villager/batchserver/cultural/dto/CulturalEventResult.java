package com.villager.batchserver.cultural.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CulturalEventResult {
    @JsonProperty("CODE")
    private String code;
    @JsonProperty("MESSAGE")
    private String message;
}

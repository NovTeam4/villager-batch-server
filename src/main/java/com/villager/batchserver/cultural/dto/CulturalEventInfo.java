package com.villager.batchserver.cultural.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CulturalEventInfo {
    @JsonProperty("list_total_count")
    private int totalCount;
    @JsonProperty("RESULT")
    private CulturalEventResult result;
    @JsonProperty("row")
    private List<CulturalEventRow> rows;
}

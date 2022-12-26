package com.villager.batchserver.event.body;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartyCreatedBody implements Serializable {
    private Long townId;
    private String townName;
    private Double latitude;
    private Double longitude;
    private int mannerPoint;
    private int memberCount;
    private Long partyId;
    private int amount;
    private String partyName;
    private List<String> tags;
}
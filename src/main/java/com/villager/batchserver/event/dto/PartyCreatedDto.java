package com.villager.batchserver.event.dto;

import com.villager.batchserver.event.body.PartyCreatedBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartyCreatedDto {
    private Long townId;
    private String townName;
    private Double latitude;
    private Double longitude;
    private int mannerPoint;
    private int memberCount;
    private Long partyId;
    private int amount;
    private String partyName;
    private String tagName;

    public static PartyCreatedDto createPartyCreated(PartyCreatedBody body, String tag) {
        return PartyCreatedDto.builder()
                .townId(body.getTownId())
                .townName(body.getTownName())
                .latitude(body.getLatitude())
                .longitude(body.getLongitude())
                .mannerPoint(body.getMannerPoint())
                .memberCount(body.getMemberCount())
                .partyId(body.getPartyId())
                .amount(body.getAmount())
                .partyName(body.getPartyName())
                .tagName(tag)
                .build();
    }
}

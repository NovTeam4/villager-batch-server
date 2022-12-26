package com.villager.batchserver.cultural.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.villager.batchserver.cultural.domain.Cultural;
import lombok.Data;

@Data
public class CulturalEventRow {
    @JsonProperty("CODENAME")
    private String codeName;
    @JsonProperty("GUNAME")
    private String guName;
    @JsonProperty("TITLE")
    private String title;
    @JsonProperty("DATE")
    private String date;
    @JsonProperty("PLACE")
    private String place;
    @JsonProperty("ORG_NAME")
    private String orgName;
    @JsonProperty("USE_TRGT")
    private String useTarget;
    @JsonProperty("USE_FEE")
    private String useFee;
    @JsonProperty("PLAYER")
    private String player;
    @JsonProperty("PROGRAM")
    private String program;
    @JsonProperty("ETC_DESC")
    private String etcDesc;
    @JsonProperty("ORG_LINK")
    private String orgLink;
    @JsonProperty("MAIN_IMG")
    private String mainImg;
    @JsonProperty("RGSTDATE")
    private String registerDate;
    @JsonProperty("TICKET")
    private String ticket;
    @JsonProperty("STRTDATE")
    private String startDate;
    @JsonProperty("END_DATE")
    private String endDate;
    @JsonProperty("THEMECODE")
    private String themeCode;

    public Cultural toEntity() {
        return Cultural.builder()
                .codeName(this.codeName)
                .guName(this.guName)
                .title(this.title)
                .date(this.date)
                .place(this.place)
                .orgName(this.orgName)
                .useTarget(this.useTarget)
                .useFee(this.useFee)
                .player(this.player)
                .program(this.program)
                .etcDesc(this.etcDesc)
                .orgLink(this.orgLink)
                .mainImg(this.mainImg)
                .registerDate(this.registerDate)
                .ticket(this.ticket)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .themeCode(this.themeCode)
                .build();
    }
}

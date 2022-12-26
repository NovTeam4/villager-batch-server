package com.villager.batchserver.config.cultural;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.villager.batchserver.config.properties.CulturalProperties;
import com.villager.batchserver.cultural.dto.CulturalEventDto;
import com.villager.batchserver.cultural.dto.CulturalEventRow;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Component
@RequiredArgsConstructor
public class CulturalEventItemReader implements ItemReader<List<CulturalEventRow>> {

    private final CulturalProperties culturalProperties;
    private final WebClient.Builder wcBuilder;
    private boolean isActive = false;
    private int currentPage;
    private int endPage;

    @Override
    public List<CulturalEventRow> read() throws Exception {
        if (!isActive) {
            isActive = true;
            initPage();
        }
        CulturalEventDto.ResponseCulturalEventApi result = getBankList(currentPage, endPage);

        if (result.getCulturalEventInfo() == null) {
            isActive = false;
            return null;
        }
        if (!result.getCulturalEventInfo().getResult().getCode().equals("INFO-000")) {
            throw new Exception();
        }


        if (result.getCulturalEventInfo().getTotalCount() > currentPage) {
            currentPage += culturalProperties.getBatchSize();
            endPage += culturalProperties.getBatchSize();
        } else {
            isActive = false;
            return null;
        }

        return result.getCulturalEventInfo().getRows();
    }

    private CulturalEventDto.ResponseCulturalEventApi getBankList(int startPage, int endPage) throws Exception {

        return wcBuilder.build().get()
                .uri(getPath(startPage, endPage))
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> Mono.error(new Exception()))
                .bodyToMono(CulturalEventDto.ResponseCulturalEventApi.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);
    }

    private void initPage() {
        currentPage = 1;
        endPage = culturalProperties.getBatchSize();
    }

    private String getPath(int startPage, int endPage) {
        return culturalProperties.getPath() +
                "/" + URLEncoder.encode(culturalProperties.getKey(), StandardCharsets.UTF_8) +
                "/" + URLEncoder.encode(culturalProperties.getType(), StandardCharsets.UTF_8) +
                "/" + URLEncoder.encode(culturalProperties.getService(), StandardCharsets.UTF_8) +
                "/" + URLEncoder.encode(String.valueOf(startPage), StandardCharsets.UTF_8) +
                "/" + URLEncoder.encode(String.valueOf(endPage), StandardCharsets.UTF_8);
    }
}
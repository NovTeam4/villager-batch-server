package com.villager.batchserver.config.cultural;

import com.villager.batchserver.cultural.domain.Cultural;
import com.villager.batchserver.cultural.dto.CulturalEventDto;
import com.villager.batchserver.cultural.dto.CulturalEventRow;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CulturalEventItemProcessor implements ItemProcessor<List<CulturalEventRow>, List<Cultural>> {

    @Override
    public List<Cultural> process(List<CulturalEventRow> item) throws Exception {
        return item.stream()
                .map(CulturalEventRow::toEntity)
                .collect(Collectors.toList());
    }
}

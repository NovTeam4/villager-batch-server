package com.villager.batchserver.event.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.villager.batchserver.config.properties.PartyProperties;
import com.villager.batchserver.event.body.PartyCreatedBody;
import com.villager.batchserver.event.channels.PartyCreatedEventChannel;
import com.villager.batchserver.event.consumer.VillagerEvent;
import com.villager.batchserver.event.domain.Member;
import com.villager.batchserver.event.domain.MemberQueryRepository;
import com.villager.batchserver.event.dto.PartyCreatedDto;
import com.villager.batchserver.event.service.VillagerEventProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

import static com.villager.batchserver.event.consumer.VillagerEventType.PARTY_CREATED_EVENT;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyCreatedEventProcessService implements VillagerEventProcessService {
    private final PartyProperties partyProperties;
    private final MemberQueryRepository memberQueryRepository;
    private final PartyCreatedEventChannel eventChannel;
    private final ObjectMapper mapper;


    @Override
    public boolean isSupport(VillagerEvent event) {
        return event.getEventType().equals(PARTY_CREATED_EVENT);
    }

    @Override
    public void process(VillagerEvent event) {
        PartyCreatedBody body = getBody(event);

        for (String tag : body.getTags()) {
            long tagTotalCount = memberQueryRepository.getTagTotalCount(body.getTownId(), tag);

            int batchSize = partyProperties.getNotify().getBatchSize();
            if(tagTotalCount > 0) {
                int loop = ((int) (tagTotalCount / batchSize)) + 1;
                for (int i = 0; i < loop; i++) {
                    List<Member> tagAttentionMember = memberQueryRepository
                            .getTagAttentionMember(body.getTownId(), tag, i, (i + batchSize) - 1);
                    if(tagAttentionMember != null && tagAttentionMember.size() > 0) {
                        for (Member member : tagAttentionMember) {
                            SseEmitter emitter = eventChannel.getEmitter(member.getId());
                            eventChannel.sendToClient(emitter, member.getId(),
                                    PartyCreatedDto.createPartyCreated(body, tag));
                            // log.info("sendToClient : {}", member.getId());
                        }
                    }
                }
            }
        }
    }

    private PartyCreatedBody getBody(VillagerEvent event) {
        return mapper.convertValue(event.getBody(), PartyCreatedBody.class);
    }
}


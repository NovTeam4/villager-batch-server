package com.villager.batchserver.config.events.party;

import com.villager.batchserver.config.properties.PartyProperties;
import com.villager.batchserver.event.body.PartyCreatedBody;
import com.villager.batchserver.event.channels.PartyCreatedEventChannel;
import com.villager.batchserver.event.domain.Member;
import com.villager.batchserver.event.domain.repository.MemberQueryRepository;
import com.villager.batchserver.event.dto.PartyCreatedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

import static com.villager.batchserver.config.events.party.PartyCreatedEventBatchConfig.PARTY_CREATED_EVENT_BATCH_CONFIG_KEY;
import static org.springframework.batch.repeat.RepeatStatus.FINISHED;

@Component
@RequiredArgsConstructor
public class PartyCreatedEventProcessTasklet implements Tasklet {
    private final PartyProperties partyProperties;
    private final MemberQueryRepository memberQueryRepository;
    private final PartyCreatedEventChannel eventChannel;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        PartyCreatedBody body = getBody(contribution);

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
                            if(emitter != null) {
                                eventChannel.sendToClient(emitter, member.getId(),
                                        PartyCreatedDto.ResponsePartyCreatedApi.createPartyCreated(body, tag));
                            }
                            // log.info("sendToClient : {}", member.getId());
                        }
                    }
                }
            }
        }

        return FINISHED;
    }

    private PartyCreatedBody getBody(StepContribution contribution) {
        return (PartyCreatedBody) contribution.getStepExecution()
                .getJobExecution()
                .getJobParameters()
                .getParameters()
                .get(PARTY_CREATED_EVENT_BATCH_CONFIG_KEY)
                .getValue();
    }
}

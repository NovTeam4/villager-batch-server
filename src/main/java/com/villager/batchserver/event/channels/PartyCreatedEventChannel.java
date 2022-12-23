package com.villager.batchserver.event.channels;

import com.villager.batchserver.config.properties.PartyProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class PartyCreatedEventChannel {
    private final PartyProperties partyProperties;
    private final ConcurrentHashMap<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(Long memberId) {
        if (!sseEmitters.contains(memberId)) {
            SseEmitter sseEmitter = new SseEmitter(partyProperties.getNotify().getTimeout());
            sseEmitters.put(memberId, sseEmitter);

            sseEmitter.onCompletion(() -> sseEmitters.remove(memberId));
            sseEmitter.onTimeout(() -> sseEmitters.remove(memberId));

            sendToClient(sseEmitter, memberId, "[memberId = " + memberId + "]");
            return sseEmitter;
        }
        return null;
    }

    public void sendToClient(SseEmitter emitter, Long id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id.toString())
                    .name(partyProperties.getName())
                    .data(data));
        } catch (IOException exception) {
            sseEmitters.remove(id);
            throw new RuntimeException("연결 오류!");
        }
    }

    public SseEmitter getEmitter(Long memberId) {
        return sseEmitters.get(memberId);
    }
}

package com.villager.batchserver.event.channels;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PartyCreatedEventChannel {
    @Value("${party.notify.time-out}")
    private Long timeOut;
    private final ConcurrentHashMap<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(Long memberId) {
        if(!sseEmitters.contains(memberId)) {
            SseEmitter sseEmitter = new SseEmitter(timeOut);
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
                    .name("create")
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

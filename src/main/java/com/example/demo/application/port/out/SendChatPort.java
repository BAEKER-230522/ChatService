package com.example.demo.application.port.out;

import com.example.demo.adaptor.dto.ChatRequest;
import reactor.core.publisher.Mono;

public interface SendChatPort {
    Mono<Void> sendChat(ChatRequest chatRequest);
}

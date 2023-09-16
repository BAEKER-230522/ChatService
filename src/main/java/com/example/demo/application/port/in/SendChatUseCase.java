package com.example.demo.application.port.in;

import com.example.demo.adaptor.dto.ChatRequest;
import reactor.core.publisher.Mono;

public interface SendChatUseCase {
    Mono<Void> sendChat(ChatRequest request);
}

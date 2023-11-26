package com.example.demo.application.port.in;

import reactor.core.publisher.Mono;

public interface SendChatUseCase {
    Mono<Void> sendChat(SendChatCommand command);
}

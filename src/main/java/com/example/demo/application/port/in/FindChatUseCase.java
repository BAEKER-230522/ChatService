package com.example.demo.application.port.in;

import com.example.demo.adaptor.dto.ChatResponse;
import reactor.core.publisher.Flux;

public interface FindChatUseCase {
    Flux<ChatResponse> findBySenderAndReceiver(ChatFindCommand command);
}

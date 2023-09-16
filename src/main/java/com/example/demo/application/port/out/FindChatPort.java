package com.example.demo.application.port.out;

import com.example.demo.adaptor.dto.ChatResponse;
import reactor.core.publisher.Flux;


public interface FindChatPort {
    Flux<ChatResponse> findChatByMemberId(Long sender, Long receiver);
}

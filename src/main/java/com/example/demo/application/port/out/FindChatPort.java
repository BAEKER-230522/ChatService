package com.example.demo.application.port.out;

import com.example.demo.domain.Chat;
import reactor.core.publisher.Flux;


public interface FindChatPort {
    Flux<Chat> findChatByMemberId(Long sender, Long receiver);
}

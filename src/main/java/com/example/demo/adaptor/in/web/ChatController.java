package com.example.demo.adaptor.in.web;

import com.example.demo.adaptor.dto.ChatRequest;
import com.example.demo.domain.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ChatController {


    @MessageMapping("chat")
    public Flux<Chat> chat(ChatRequest chatRequest) {
        return null;
    }
}

package com.example.demo.adaptor.in.web;

import com.example.demo.adaptor.dto.ChatRequest;
import com.example.demo.adaptor.dto.ChatResponse;
import com.example.demo.application.port.in.FindChatUseCase;
import com.example.demo.application.port.in.SendChatUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SendChatUseCase sendChatUseCase;
    private final FindChatUseCase findChatUseCase;

    @MessageMapping("/chat")
    public Mono<Void> chat(ChatRequest chatRequest) {
        return sendChatUseCase.sendChat(chatRequest);
    }

    @MessageMapping("/chat/{sender}/{receiver}")
    public Flux<ChatResponse> findChat(@PathVariable("sender") Long sender, @PathVariable("receiver") Long receiver) {
        return findChatUseCase.findBySenderAndReceiver(sender, receiver);
    }
}
